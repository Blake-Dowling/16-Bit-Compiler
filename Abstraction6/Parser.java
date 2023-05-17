import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * Author: Blake Dowling
 * Project: Virtual Machine I
 * Parser.java - Reads given .vm file, line by line, in order to translate the file through invocation
 * of the CodeWriter class's methods.
 */
public class Parser {
    private FileInputStream fileInputStream;
    private Scanner scanner;
    private String command;
    private String arg1;
    private int arg2;
    private CommandType commandType;

    /*
     * Description: Initializes Parser object with FileInputStream reading from given .vm file.
     * Precondition: fileName contains .vm extension.
     * Postcondition: Parser object is initialized to read from given .vm file.
     */
    public Parser(String fileName) {
        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(fileInputStream);
    }

    /*
     * Description: Returns true if there are more lines available to read from .vm file.
     * Precondition: FileInputStream is open.
     * Postcondition: Returns true if scanner has next line, else false.
     */
    public boolean hasMoreCommands() {
        if (scanner.hasNextLine()) {
            return true;
        }
        return false;
    }

    /*
     * Description: Obtains next line of .vm file, parses line into commandType, arg1, and arg2 parts.
     * Precondition: FileInputStream is open, scanner has next line.
     * Postcondition: Next line of .vm file is parsed.
     */
    public void advance() {
        String line = scanner.nextLine();
        if (line.contains("//")) {
            line = line.substring(0, line.indexOf("/")).trim();
        }
        System.out.println(line);

        String[] splitLine = line.split(" ");
        command = splitLine[0];
        if (splitLine.length > 1) { //.vm line has multiple parts.
            arg1 = splitLine[1];
            arg2 = Integer.valueOf(splitLine[2]);

        } else { //.vm line only has one part.
            arg1 = null;
            arg2 = 0;
        }
        switch (command) { // First part of command.
            case "add":
            case "sub":
            case "neg":
            case "eq":
            case "gt":
            case "lt":
            case "and":
            case "or":
            case "not": {
                commandType = CommandType.C_ARITHMETIC;
                break;
            }
            case "push": {
                commandType = CommandType.C_PUSH;
                break;
            }
            case "pop": {
                commandType = CommandType.C_POP;
                break;
            }
            case "label": {
                commandType = CommandType.C_LABEL;
            }

        }
    }

    /*
     * Description: Accessor for command variable.
     * Precondition: command has been initialized.
     * Postcondition: Returns command.
     */
    public String getCommand() {
        return command;
    }

    /*
     * Description: Accessor for arg1 variable.
     * Precondition: arg1 has been initialized.
     * Postcondition: Returns arg1.
     */
    public String getArg1() {
        return arg1;
    }

    /*
     * Description: Accessor for arg2 variable.
     * Precondition: arg1 has been initialized.
     * Postcondition: Returns arg2.
     */
    public int getArg2() {
        return arg2;
    }

    /*
     * Description: Accessor for commandType variable.
     * Precondition: commandType has been initialized.
     * Postcondition: Returns commandType.
     */
    public CommandType getCommandType() {
        return commandType;
    }

    /*
     * Description: Closes fileInputStream..
     * Precondition: fileInputStream has been initialized.
     * Postcondition: Allows VMTranslator class to close fileInputStream.
     */
    public void close() {
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

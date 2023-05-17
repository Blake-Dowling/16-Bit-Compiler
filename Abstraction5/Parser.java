import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/* Parser.java - Object used to read .asm files and store lines as variables which the Assembler
 * translates and writes into Hack code.
 * Author: Blake Dowling
 * Project: Assembler
 */
public class Parser {
    private FileInputStream fileInputStream;
    private Scanner scanner;
    private int lineNumber;
    private String cleanLine;
    private String destMnemonic;
    private String compMnemonic;
    private String jumpMnemonic;
    private Command commandType;
    private String symbol;
    private SymbolTable symbolTable;
    private String address;

    /*
     * Description: Initializes FileInputStream and Scanner using given file name.
     * If file cannot be opened, ends program with error message. lineNumber instance variable is initialized to -1.
     * Precondition: Given file name is of type .asm
     * Postcondition: Instance of Parser is initialized with a FileInputStream and Scanner.
     */
    public Parser(String fileName) {
        symbolTable = new SymbolTable();
        lineNumber = -1;
        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(fileInputStream);
    }

    /* Description: Initializes instance of parser without initializing symbolTable. This allows the Assembler
     * class to return to the top of the .asm file in order to go from the first pass to the second pass
     * while keeping all symbol/address pairs previously stored in the symbolTable.
     * Precondition: instance of Parser has been initialized.
     * Postcondition: FileInputStream and Scanner used to interpret .asm file are re-initialized,
     * and symbolTable values stored during the first pass remain stored.
     */
    public void resetParser(String fileName) {
        lineNumber = -1;
        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(fileInputStream);
    }

    /*
     * Description: Removes comments from given rawLine which begin with "//".
     * Precondition: rawLine argument is passed.
     * Postcondition: rawLine is returned without comments after "//" and spaces are removed.
     */
    public String cleanLine(String rawLine) {
        if (rawLine.contains("//")) {
            rawLine = rawLine.substring(0, rawLine.indexOf("/")); //Remove comments after "//".
        }
        return rawLine.trim(); //Remove spaces.
    }

    /*
     * Description: Accessor method for cleanLine instance variable.
     * Precondition: cleanLine variable has a value;
     * Postcondition: cleanLine variable is returned.
     */
    public String getCleanLine() {
        return cleanLine;
    }

    /*
     * Description: Accessor method for lineNumber instance variable.
     * Precondition: lineNumber variable has a value;
     * Postcondition: lineNumber variable is returned.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /*
     * Description: Accessor method for destMnemonic instance variable.
     * Precondition: destMnemonic variable has a value;
     * Postcondition: destMnemonic variable is returned.
     */
    public String getDestMnemonic() {
        return destMnemonic;
    }

    /*
     * Description: Accessor method for compMnemonic instance variable.
     * Precondition: compMnemonic variable has a value;
     * Postcondition: compMnemonic variable is returned.
     */
    public String getCompMnemonic() {
        return compMnemonic;
    }

    /*
     * Description: Accessor method for jumpMnemonic instance variable.
     * Precondition: jumpMnemonic variable has a value;
     * Postcondition: jumpMnemonic variable is returned.
     */
    public String getJumpMnemonic() {
        return jumpMnemonic;
    }

    /*
     * Description: Accessor method for symbol instance variable.
     * Precondition: symbol variable has a value;
     * Postcondition: symbol variable is returned.
     */
    public String getSymbol() {
        return symbol;
    }

    /*
     * Description: Accessor method for commandType instance variable.
     * Precondition: commandType variable has a value;
     * Postcondition: commandType variable is returned.
     */
    public Command getCommandType() {
        return commandType;
    }

    /*
     * Description: Assigns cleaned string of next line in .asm file to cleanLine variable
     * and increments lineNumber variable.
     * Precondition: Scanner has next line in .asm file.
     * Postcondition: cleanLine variable is set to next line, lineNumber variable is incremented,
     * and scanner line is incremented.
     */
    public void advance() {
        if (fileInputStream != null && scanner.hasNextLine()) {
            cleanLine = cleanLine(scanner.nextLine());
            lineNumber++;
        }
    }

    /* Description: Sets commandType variable according to identifying characters contained by cleanLine.
     * Precondition: Advance has been called, and cleanline is not null.
     * Postcondition: commandType variable is set to corresponding command type.
     */
    public void parseCommandType() {
        if (cleanLine != null) {
            if (cleanLine.contains("(")) {
                commandType = Command.L_COMMAND;
            } else if (cleanLine.contains("@")) {
                commandType = Command.A_COMMAND;
            } else if (cleanLine.contains("=") || cleanLine.contains(";")) {
                commandType = Command.C_COMMAND;
            } else {
                commandType = Command.NO_COMMAND;
            }
        }
    }

    /* Description: Coordinates invocation of parseSymbol, parseDest, parseComp, and parseJump
     * methods according to value of commandType variable.
     * Precondition: advance has been called, cleanLine variable is not null.
     * Postcondition: the current line in .asm file is parsed, allowing Assembler class
     * to translate and write current instance variable values to Hack file.
     */
    public void parse() {
        symbol = null;
        destMnemonic = null;
        compMnemonic = null;
        jumpMnemonic = null;
        parseCommandType();
        if (commandType == Command.L_COMMAND) { // First Pass
            parseSymbol();
        } else if (commandType == Command.A_COMMAND) {
            parseSymbol();
        } else if (commandType == Command.C_COMMAND) { //Second Pass
            parseDest();
            parseComp();
            parseJump();
        } else if (commandType == Command.NO_COMMAND) {
            lineNumber--; //No-command lines should not count as lines after parsing.
        }
    }

    /* Description: Assigns data before "=" to destMnemonic variable.
     * Precondition: Advance has been called, cleanLine is not null.
     * Postcondition: destMnemonic variable is given dest value of line in .asm file.
     */
    public void parseDest() {
        destMnemonic = null; //For each line, initialize to null until determined contains a "=".
        if (cleanLine.contains("=")) {
            destMnemonic = cleanLine.substring(0, cleanLine.indexOf("="));

        }
    }

    /* Description: Assigns data after "=" and before ";" to compMnemonic variable.
     * Precondition: Advance has been called, cleanLine is not null.
     * Postcondition: compMnemonic variable is given comp value of line in .asm file.
     */
    public void parseComp() {
        compMnemonic = null; //Initialize to null for eachLine until c command type determined.
        if (cleanLine.contains("=")) {
            compMnemonic = cleanLine.substring(cleanLine.indexOf("=") + 1, cleanLine.length());
        }
        if (cleanLine.contains(";")) {
            compMnemonic = cleanLine.substring(0, cleanLine.indexOf(";"));
        }
    }

    /* Description: Assigns data after ";" to jumpMnemonic variable.
     * Precondition: Advance has been called, cleanLine is not null.
     * Postcondition: jumpMnemonic variable is given dest value of line in .asm file.
     */
    public void parseJump() {
        jumpMnemonic = null;
        if (cleanLine.contains(";")) {
            jumpMnemonic = cleanLine.substring(cleanLine.indexOf(";") + 1);
        }
    }

    /* Description: Parses labels, label references, and variables in .asm code.
     * Precondition: Advance has been called, cleanLine is not null.
     * Postcondition: Line has been parsed; corresponding variables have been updated, and
     * labels or variables have been added to or retrieved from symbolTable.
     */
    public void parseSymbol() {
        if (cleanLine.contains("(")) { //Encountering label during first pass.
            symbol = cleanLine.substring(cleanLine.indexOf("(") + 1, cleanLine.indexOf(")"));//Remove parentheses.
            if (!symbolTable.contains(symbol)) {
                symbolTable.addEntry(symbol, Integer.toString(lineNumber));//Add symbol and its line number to symbolTable.
            }
            lineNumber--; // During Second Pass, we do not want to count labels as lines.
        }
//A commands beginning with upper case letters are assumed to be label references.
        if (cleanLine.contains("@")) {//Address if contains only numbers, label reference if contains uppercase, otherwise variable.
            symbol = cleanLine.substring(cleanLine.indexOf("@") + 1); //Remove "@".
            if (!symbol.matches("[0_9]") && !symbolTable.contains(symbol)) //Variable will be added to symbolTable during first pass.
            {
                if (!Character.isUpperCase(symbol.charAt(0)))//This is assumbed to be a variable by Hack convention, due to beginning with lower case.
                {
                    symbolTable.addVariable(symbol);
                }
            } else if (!Character.isDigit(symbol.charAt(0))) { //Second Pass, symbol is in table.
                symbol = symbolTable.getAddress(symbol);//Assign numberical address to symbol variable.
            } //Else symbol remains a decimal number String.
        }
    }

    /* Description: Determines if Parser has more commands.
     * Precondition: Scanner has been initialized.
     * Postcondition: Returns true if scanner has next line, else false.
     */
    public boolean hasMoreCommands() {
        return scanner.hasNextLine();
    }

    /* Description: Method allowing Assembler class to close FileInputStream used by Parser class.
     * Precondition: FileInputStream has been initialized.
     * Postcondition: FileInputStream is closed.
     */
    public void closeFileInputStream() {
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

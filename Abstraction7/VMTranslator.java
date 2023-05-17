import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/*
 * Author: Blake Dowling
 * Project: Virtual Machine II
 * VMTranslator.java - Driver controlling which .vm and .asm files Parser and CodeWriter classes
 * read from and write to.
 */
public class VMTranslator {
    static Parser parser;
    static String VM_FILE_1;
    static String VM_FILE_2;
    static String VM_FILE_3;
    static String VM_FILE_4;
    static String VM_FILE_5;
    static String VM_FILE_6;
    static String VM_FILE_7;
    static String ASM_FILE_1;
    static String ASM_FILE_2;
    static String ASM_FILE_3;
    static String ASM_FILE_4;
    static String ASM_FILE_5;
    static String ASM_FILE_6;
    static String VM_DIR;

    /*
     * Description: Main method, runs instances of Parser and Codewriter objects in order to translate
     * from .vm file to .asm file.
     * Precondition: Given .vm files exist.
     * Postcondition: .asm file will be translated from .vm file, and FileWriter and FileInputStream will be closed.
     */
    public static void main(String[] args) {
        //Input file names.
        VM_DIR = "/Users/blakedowling/IdeaProjects/CS220HW9_1/src/";
        VM_FILE_1 = "/Users/blakedowling/IdeaProjects/CS220HW9_1/src/BasicLoop.vm";
        VM_FILE_2 = "/Users/blakedowling/IdeaProjects/CS220HW9_1/src/FibonacciSeries.vm";
        VM_FILE_3 = "/Users/blakedowling/IdeaProjects/CS220HW9_1/src/SimpleFunction.vm";
        VM_FILE_4 = "/Users/blakedowling/IdeaProjects/CS220HW9_1/src/Sys.vm";
        VM_FILE_5 = "/Users/blakedowling/IdeaProjects/CS220HW9_1/src/Class1.vm";
        VM_FILE_6 = "/Users/blakedowling/IdeaProjects/CS220HW9_1/src/Class2.vm";
        VM_FILE_7 = "/Users/blakedowling/IdeaProjects/CS220HW9_1/src/Main.vm";
        //Output File names.
        ASM_FILE_1 = "BasicLoop.asm";
        ASM_FILE_2 = "FibonacciSeries.asm";
        ASM_FILE_3 = "SimpleFunction.asm";
        ASM_FILE_4 = "StaticsTest.asm";
        ASM_FILE_5 = "NestedCall.asm";
        ASM_FILE_6 = "FibonacciElement.asm";

        parser = new Parser(VM_FILE_4);
        CodeWriter codeWriter = new CodeWriter(ASM_FILE_4);
        codeWriter.writeInit();
        write(parser, codeWriter);

        parser = new Parser(VM_FILE_5);
        write(parser, codeWriter);

        parser = new Parser(VM_FILE_6);
        write(parser, codeWriter);

        codeWriter.close();
    }

    /*
     * Description: Invokes methods of CodeWriter and Parser classes to iterate through .vm files and translate each line
     * onto an .asm file.
     * Precondition: CodeWriter and Parser are not null.
     * Postcondition: Each line of .vm files are iterated, translated to .asm, and Parser is closed.
     */
    public static void write(Parser parser, CodeWriter codeWriter) {

        while (parser.hasMoreCommands()) {
            parser.advance(); //Next Line in .vm file. + parse.
            CommandType command = parser.getCommandType(); //Command type of first part of .vm line.
            String arg1 = parser.getArg1(); //First argument of .vm line.
            int arg2 = parser.getArg2(); //Second argument of .vm line.
            System.out.println(command + arg1 + arg2);
            try {
                switch (command) {
                    case C_ARITHMETIC: {//Use CodeWriter to write translated arithmetic command.
                        codeWriter.writeArithmetic(parser.getCommand()); //Passes specific arithmetic command name.
                        break;
                    }
                    case C_PUSH:
                    case C_POP: {//Use CodeWriter to write translated push or pop command.
                        codeWriter.writePushPop(command, arg1, arg2);
                        break;
                    }
                    case C_LABEL: {
                        codeWriter.writeLabel(arg1);
                        break;
                    }
                    case C_GOTO: {
                        codeWriter.writeGoto(arg1);
                        break;
                    }
                    case C_IF: {
                        codeWriter.writeIf(arg1);
                        break;
                    }
                    case C_FUNCTION: {
                        codeWriter.writeFunction(arg1, arg2);
                        break;
                    }
                    case C_CALL: {
                        codeWriter.writeCall(arg1, arg2);
                        break;
                    }
                    case C_RETURN: {
                        codeWriter.writeReturn();
                        break;
                    }

                }
            } catch (NullPointerException e) {
                System.out.println("null command type");
            }
        }
        parser.close();
    }
}

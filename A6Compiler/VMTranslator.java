/*
 * Author: Blake Dowling
 * Project: Virtual Machine I
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
    static String ASM_FILE_1;
    static String ASM_FILE_2;
    static String ASM_FILE_3;
    static String ASM_FILE_4;
    static String ASM_FILE_5;

    /*
     * Description: Main method, runs instances of Parser and Codewriter objects in order to translate
     * from .vm file to .asm file.
     * Precondition: Given .vm files exist.
     * Postcondition: .asm file will be translated from .vm file, and FileWriter and FileInputStream will be closed.
     */
    public static void main(String[] args) {
        //Input file names.
        VM_FILE_1 = "/Users/blakedowling/IdeaProjects/CS220HW8_3/src/BasicTest.vm";
        VM_FILE_2 = "/Users/blakedowling/IdeaProjects/CS220HW8_3/src/PointerTest.vm";
        VM_FILE_3 = "/Users/blakedowling/IdeaProjects/CS220HW8_3/src/SimpleAdd.vm";
        VM_FILE_4 = "/Users/blakedowling/IdeaProjects/CS220HW8_3/src/StackTest.vm";
        VM_FILE_5 = "/Users/blakedowling/IdeaProjects/CS220HW8_3/src/StaticTest.vm";
        //Output File names.
        ASM_FILE_1 = "BasicTest.asm";
        ASM_FILE_2 = "PointerTest.asm";
        ASM_FILE_3 = "SimpleAdd.asm";
        ASM_FILE_4 = "StackTest.asm";
        ASM_FILE_5 = "StaticTest.asm";

        parser = new Parser(VM_FILE_5);
        CodeWriter codeWriter = new CodeWriter(ASM_FILE_5);
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
                    case C_LABEL:
                    {
                        codeWriter.writeLabel(arg1);
                    }

                }
            } catch (NullPointerException e) {
                System.out.println("null command type");
            }
        }
        parser.close();
        codeWriter.close();
    }
}

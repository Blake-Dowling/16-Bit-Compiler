import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

/* Assembler.java - Coordinates the assembly process through invocation of Parser, CInstructionMapper,
 * and FileWriter classes.
 * Author: Blake Dowling
 * Project: Assembler
 */
public class Assembler {

    private static final String asmFile1 = "/Users/blakedowling/IdeaProjects/CS220HW7_2/src/Rect.asm";
    private static Parser parser;
    private static CInstructionMapper cInstructionMapper;
    private static final String hackFile1 = "Rect.hack";
    private static FileWriter fileWriter;

    /* Description: Main method. Initializes Parser, CInstructionMapper, and FileWriter objects.
     * Coordinates invocations of firstPass and secondPass methods.
     * Precondition: .asm file exists.
     * Postcondition: .asm file is correctly translated into .hack language, and written onto .hack file.
     */
    public static void main(String[] args) {
        parser = new Parser(asmFile1);
        cInstructionMapper = new CInstructionMapper();
        try {
            fileWriter = new FileWriter(hackFile1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("First Pass:");
        firstPass();
        parser.resetParser(asmFile1);
        System.out.println("Second pass");
        secondPass();
        parser.closeFileInputStream();
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Description: Coordinates Parser's adding of labels and variables to symbolTable.
     * Precondition: FileWriter and Parser objects have been instantiated.
     * Postcondition: all labels and variables, and their corresponding address values are added to the
     * Parser's symbolTable through invocation of the Parser's advance and parse methods.
     */
    public static void firstPass() {
        while (parser.hasMoreCommands()) {
            parser.advance();
            parser.parse();
            //Displays lines as code runs.
            if (parser.getCommandType() == Command.L_COMMAND) {
                System.out.println(parser.getLineNumber() + ":s:" + parser.getSymbol());

            } else {
                System.out.println(parser.getLineNumber() + ":" + parser.getCleanLine());
            }
        }
    }

    /* Description: Coordinates Parser's parsing of .asm file lines, translates the lines
     * into Hack language, and writes each line into .hack file.
     * Precondition: FileWriter and Parser objects have been instantiated.
     * Postcondition: A .hack file containing a correct translation of the given .asm
     * file is created.
     */
    public static void secondPass() {
        while (parser.hasMoreCommands()) {
            parser.advance();
            parser.parse();
            System.out.println("---------------------------------");
            System.out.println(parser.getLineNumber() + ":" + parser.getCleanLine());
            if (parser.getCommandType() == Command.A_COMMAND) {
                writeLine(buildAInstruction()); //Write the translated a-instruction line to .hack file.
                printAInstruction();

            } else if (parser.getCommandType() == Command.C_COMMAND) {
                writeLine(buildCInstruction()); //Write the translated c-instruction line to .hack file.
                printCInstruction();
            }

        }
    }

    /* Description: writes given String to the hack file.
     * Precondition: FileWriter has been initialized.
     * Postcondition: Line is written to .hack file.
     */
    public static void writeLine(String line) {
        try {
            fileWriter.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Description: Returns
     *
     */
    public static String decimalToBinary(int decimal) {
        return Integer.toBinaryString(decimal);
    }

    /* Description: translates symbol variable from parser into a Hack binary-value a-instruction.
     * Precondition: Parser has a value from symbol variable.
     * Postcondition: Returns a binary (Hack) translation of the symbol variable from the parser
     * in order to write the a-instruction to the .hack file.
     */
    public static String buildAInstruction() {
        String aInstruction = Integer.toBinaryString(Integer.parseInt(parser.getSymbol()));
        BigInteger aInt = new BigInteger(aInstruction);
        return String.format("%016d", aInt);
    }

    /*
     * Prints the translated a-instruction from current line of .asm file.
     */
    public static void printAInstruction() {
        System.out.println(parser.getLineNumber() + ":a:" + buildAInstruction());
    }

    /* Description: Concatenates "111" with comp, dest, and jump mnemonics from parser in order
     * to build the c-instruction from the current line to be written to the .hack file.
     * Precondition: Parser has values from compMnemonic, destMnemonic, and jumpMnemonic.
     * Postcondition: Returns Hack-translated c-instruction representing current line in .asm
     * file in order to write the line into the .hack file.
     */
    public static String buildCInstruction() {
        return ("111"
                + cInstructionMapper.getComp(parser.getCompMnemonic())
                + cInstructionMapper.getDest(parser.getDestMnemonic())
                + cInstructionMapper.getJump(parser.getJumpMnemonic()));
    }

    /*
     * Prints the current c-instruction translation.
     */
    public static void printCInstruction() {
        System.out.println(parser.getLineNumber() + ":" +
                "c:" + cInstructionMapper.getComp(parser.getCompMnemonic()) +
                "d:" + cInstructionMapper.getDest(parser.getDestMnemonic()) +
                "j:" + cInstructionMapper.getJump(parser.getJumpMnemonic()));
    }
}

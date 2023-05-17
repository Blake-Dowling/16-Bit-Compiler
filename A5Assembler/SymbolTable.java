import java.util.HashMap;

/* SymbolTable.java - Object representing SymbolTable used to store symbol/address pairs.
 * Author: Blake Dowling
 * Project: Assembler
 */
public class SymbolTable {
    private HashMap<String, String> symbolTable;
    private int variableCount;

    /* Description: Initializes instance of SymbolTable class represented by a HashMap with predefined
     * symbols following book appendix.
     * Precondition: All HashMap values must have valid address integer.
     * Postcondition: Instance of SymbolTable class created.
     */
    public SymbolTable() {
        symbolTable = new HashMap<String, String>();
        variableCount = 0; //Used to define address of newly-added variable.
//predefined symbols
        symbolTable.put("SP", "0");
        symbolTable.put("LCL", "1");
        symbolTable.put("ARG", "2");
        symbolTable.put("THIS", "3");
        symbolTable.put("THAT", "4");
//registers 0-15
        symbolTable.put("R0", "0");
        symbolTable.put("R1", "1");
        symbolTable.put("R2", "2");
        symbolTable.put("R3", "3");
        symbolTable.put("R4", "4");
        symbolTable.put("R5", "5");
        symbolTable.put("R6", "6");
        symbolTable.put("R7", "7");
        symbolTable.put("R8", "8");
        symbolTable.put("R9", "9");
        symbolTable.put("R10", "10");
        symbolTable.put("R11", "11");
        symbolTable.put("R12", "12");
        symbolTable.put("R13", "13");
        symbolTable.put("R14", "14");
        symbolTable.put("R15", "15");
//first screen address and keyboard address
        symbolTable.put("SCREEN", "16384");
        symbolTable.put("KBD", "24576");

    }

    /*
     * Adds a new symbol/address pair to the symbolTable HashMap.
     * Returns true if symbol/address pair is added.
     * Returns false and does not add symbol/address pair if given symbol is not valid or is already in symbolTable.
     */
    public boolean addEntry(String symbol, String address) {
        if (!validName(symbol) || symbolTable.containsKey(symbol)) // Given symbol is not valid or is in symbolTable.
        {
            return false;
        }
        symbolTable.put(symbol, address); //Add symbol/address pair to symbolTable HashMap.
        return true; // Given symbol is valid and was not already in symbolTable.
    }

    /*
     * Returns the address represented by the given symbol.
     */
    public String getAddress(String symbol) {
        return symbolTable.get(symbol);
    }

    /*
     * Returns true if the symbolTable HashMap contains the given symbol as one of its keys.
     */
    public boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    /*
     * Used to add variable to the symbolTable. Assigns its address value to base address of 16
     * + incremented variableCount value.
     */
    public void addVariable(String variable) {
        symbolTable.put(variable, Integer.toString(16 + variableCount));
        variableCount++;
    }

    /*
     * Returns true if given symbol contains no invalid characters. Or else returns false.
     */
    public boolean validName(String symbol) {
        if (symbol == null) {
            return false;
        }
        if (!(symbol.matches("[a-zA-Z_][a-zA-Z0-9_]*")) && !(symbol.matches("[$][:][0-9]*"))) // Given symbol contains invalid character.
        {
            return false;
        }
        return true; // Given symbol contains no invalid characters.
    }
}

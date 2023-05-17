import java.util.HashMap;
/* CInstructionMapper.java - HashMaps storing binary representations of dest, comp, and jump segments of c-commands.
 * Author: Blake Dowling
 * Project: Assembler
 */
public class CInstructionMapper {
    private HashMap<String, String> destCodes;
    private HashMap<String, String> compCodes;
    private HashMap<String, String> jumpCodes;

    /* Description: Initializes HashMaps holding dest, comp, and jump codes.
     * Precondition: None.
     * Postcondition: HashMaps containing all code translations for .asm to .hack are initialized.
     */
    public CInstructionMapper() {
        destCodes = new HashMap<String, String>();
        compCodes = new HashMap<String, String>();
        jumpCodes = new HashMap<String, String>();
//Destination values
        destCodes.put(null, "000");
        destCodes.put("M", "001");
        destCodes.put("D", "010");
        destCodes.put("MD", "011");
        destCodes.put("A", "100");
        destCodes.put("AM", "101");
        destCodes.put("AD", "110");
        destCodes.put("AMD", "111");
//Computation values
        compCodes.put("0", "0101010");
        compCodes.put("1", "0111111");
        compCodes.put("-1", "0111010");
        compCodes.put("D", "0001100");
        compCodes.put("A", "0110000");
        compCodes.put("!D", "0001101");
        compCodes.put("!A", "0110001");
        compCodes.put("-D", "0001111");
        compCodes.put("-A", "0110011");
        compCodes.put("D+1", "0011111");
        compCodes.put("A+1", "0110111");
        compCodes.put("D-1", "0001110");
        compCodes.put("A-1", "0110010");
        compCodes.put("D+A", "0000010");
        compCodes.put("D-A", "0010011");
        compCodes.put("A-D", "0000111");
        compCodes.put("D&A", "0000000");
        compCodes.put("D|A", "0010101");
        compCodes.put("M", "1110000");
        compCodes.put("!M", "1110001");
        compCodes.put("-M", "1110011");
        compCodes.put("M+1", "1110111");
        compCodes.put("M-1", "1110010");
        compCodes.put("D+M", "1000010");
        compCodes.put("D-M", "1010011");
        compCodes.put("M-D", "1000111");
        compCodes.put("D&M", "1000000");
        compCodes.put("D|M", "1010101");
//Jump values
        jumpCodes.put(null, "000");
        jumpCodes.put("JGT", "001");
        jumpCodes.put("JEQ", "010");
        jumpCodes.put("JGE", "011");
        jumpCodes.put("JLT", "100");
        jumpCodes.put("JNE", "101");
        jumpCodes.put("JLE", "110");
        jumpCodes.put("JMP", "111");
    }
/* Description: Accessor method for destCode corresponding to given dest mnemonic.
 * Precondition: Given mnemonic is .asm language.
 * Postcondition: Returns .hack translation of .asm dest menmonic.
 */
    public String getDest(String mnemonic) {
        return destCodes.get(mnemonic);
    }
    /* Description: Accessor method for compCode corresponding to given comp mnemonic.
     * Precondition: Given mnemonic is .asm language.
     * Postcondition: Returns .hack translation of .asm comp menmonic.
     */
    public String getComp(String mnemonic) {
        return compCodes.get(mnemonic);
    }
    /* Description: Accessor method for jumpCode corresponding to given jump mnemonic.
     * Precondition: Given mnemonic is .asm language.
     * Postcondition: Returns .hack translation of .asm jump menmonic.
     */
    public String getJump(String mnemonic) {
        return jumpCodes.get(mnemonic);
    }
}

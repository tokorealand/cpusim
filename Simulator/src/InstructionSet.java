
import java.math.BigInteger;


/**
 * The InstructionSet class manages all instructions and is used for checking strings for
 * any assembly commands and executes them if found.
 */
public class InstructionSet {
    private Instruction[] ins = new Instruction[26];
    private int current=0;
    private Operator op = new Operator();

    /**
     * Constructor
     */
    public InstructionSet()
    {

    }

    /**
     * Sends the required components to the operator.
     * @param reg the registers
     * @param flags the flags
     * @param mem the main memory
     */
    void establishOperator(Register reg, int[] flags, Memory mem)
    {
        op.setComponents(reg,flags,mem);
    }

    /**
     * Adds an instruction to the array.
     * @param name the name of the instruction
     * @param opcode the opcode of the instruction
     * @param type the type of the instruction
     */
    void addInstruction(String name, String opcode, String type)
    {
        ins[current]=new Instruction( name, opcode, type);
        current++;
    }


    /**
     * Looks for a match to the given opcode and returns that instruction.
     * @param opcode the opcode being searched for
     * @return null or the instruction found
     */
    private Instruction checkForOp(String opcode) {

        for (int i=0; i<ins.length; i++)
        {

            if(Integer.parseInt(opcode.substring(2),16) == Integer.parseInt(ins[i].opcode.substring(2),16) ||
                    Integer.parseInt(opcode.substring(2),16) == Integer.parseInt(ins[i].opcode2.substring(2),16)) return ins[i];

        }
        return null;
    }


    /**
     * Parses the given string for a command.
     * If command is found - it is decoded and executed
     * Otherwise returns a empty string.
     * @param bitCommand the strign being checked
     * @return the command string(assembly representaion of binary) or a blank string
     */
    String parseMemoryForInstruction(String bitCommand) {
        char[] memlineBin = bitCommand.toCharArray();
        char[] opcodeCheck = new char[11];
        String command = "";
        for (int i = 0; i < 11; i++) {
            if (i < 11) {
                opcodeCheck[i] = memlineBin[i];
            }
        }



        Instruction iR = checkForOp("0x" + binarytoHex(String.valueOf(opcodeCheck)));

        if (iR != null) {

            if (iR.name.equals("HALT")) return "HALT";


            if (iR.type.equals("R")) {

                if(iR.name.equals("LSL") || iR.name.equals("LSR"))
                {
                    char[] opcode = new char[11];
                    char[] rd = new char[5];
                    char[] rm = new char[5];
                    char[] shamt = new char[6];
                    char[] rn = new char[5];

                    for (int i = 0; i < memlineBin.length; i++) {
                        if (i < 11) {
                            opcode[i] = memlineBin[i];
                        }
                        if (i > 10 && i < 16) {
                            rm[i - 11] = memlineBin[i];
                        }
                        if (i > 15 && i < 22) {
                            shamt[i - 16] = memlineBin[i];
                        }
                        if (i > 21 && i < 27) {
                            rn[i - 22] = memlineBin[i];
                        }
                        if (i > 26) {
                            rd[i - 27] = memlineBin[i];
                        }

                    }
                    op.executeOperationR(iR.name, String.valueOf(rd), String.valueOf(rm), String.valueOf(shamt), String.valueOf(rn));

                    command = iR.name +
                            " X" + String.valueOf(Integer.parseInt(String.valueOf(rd), 2)) +
                            ", X" + String.valueOf(Integer.parseInt(String.valueOf(rn), 2)) +
                            ", #" + String.valueOf(Integer.parseInt(String.valueOf(shamt), 2) + ";");

                }
                else {
                    char[] opcode = new char[11];
                    char[] rd = new char[5];
                    char[] rm = new char[5];
                    char[] shamt = new char[6];
                    char[] rn = new char[5];

                    for (int i = 0; i < memlineBin.length; i++) {
                        if (i < 11) {
                            opcode[i] = memlineBin[i];
                        }
                        if (i > 10 && i < 16) {
                            rm[i - 11] = memlineBin[i];
                        }
                        if (i > 15 && i < 22) {
                            shamt[i - 16] = memlineBin[i];
                        }
                        if (i > 21 && i < 27) {
                            rn[i - 22] = memlineBin[i];
                        }
                        if (i > 26) {
                            rd[i - 27] = memlineBin[i];
                        }

                    }
                    op.executeOperationR(iR.name, String.valueOf(rd), String.valueOf(rm), String.valueOf(shamt), String.valueOf(rn));

                    command = iR.name +
                            " X" + String.valueOf(Integer.parseInt(String.valueOf(rd), 2)) +
                            ", X" + String.valueOf(Integer.parseInt(String.valueOf(rn), 2)) +
                            ", X" + String.valueOf(Integer.parseInt(String.valueOf(rm), 2) + ";");

                }
            }

            if (iR.type.equals("I")) {
                char[] opcode = new char[11];
                char[] alu = new char[12];
                char[] rd = new char[5];
                char[] rn = new char[5];

                for (int i = 0; i < memlineBin.length; i++) {
                    if (i < 11) {
                        opcode[i] = memlineBin[i];
                    }
                    if (i > 9 && i < 22) {
                        alu[i - 10] = memlineBin[i];
                    }
                    if (i > 21 && i < 27) {
                        rn[i - 22] = memlineBin[i];
                    }
                    if (i > 26) {
                        rd[i - 27] = memlineBin[i];
                    }

                }

                String rdS = String.valueOf(rd);
                String rnS = String.valueOf(rn);
                String aluS = String.valueOf(alu);


                op.executeOperationI(iR.name, aluS, rdS, rnS);


                if (String.valueOf(alu).length() % 4 == 0 && String.valueOf(alu).substring(0, 1).equals("1")) {
                    int differences32 = 32 - String.valueOf(alu).length();
                    String sigFiller = new String(new char[differences32]).replace("\0", "1");
                    aluS = sigFiller + String.valueOf(alu);
                    int aluI = new BigInteger(aluS, 2).intValue();
                    aluS = String.valueOf(aluI);


                } else {
                    aluS = String.valueOf(Integer.parseInt(aluS, 2));
                }


                command = iR.name +
                        " X" + String.valueOf(Integer.parseInt(rdS, 2)) +
                        ", X" + String.valueOf(Integer.parseInt(rnS, 2)) +
                        ", #" + aluS + ";";

            }
            if (iR.type.equals("D")) {
                char[] opcode = new char[11];
                char[] rt = new char[5];
                char[] dtAdd = new char[9];
                char[] opp = new char[2];
                char[] rn = new char[5];

                for (int i = 0; i < memlineBin.length; i++) {
                    if (i < 11) {
                        opcode[i] = memlineBin[i];
                    }
                    if (i > 10 && i < 20) {
                        dtAdd[i - 11] = memlineBin[i];
                    }
                    if (i > 19 && i < 22) {
                        opp[i - 20] = memlineBin[i];
                    }
                    if (i > 21 && i < 27) {
                        rn[i - 22] = memlineBin[i];
                    }
                    if (i > 26) {
                        rt[i - 27] = memlineBin[i];
                    }

                }

                String rtS = String.valueOf(rt);
                String rnS = String.valueOf(rn);
                String dtAddS = String.valueOf(dtAdd);

                op.executeOperationD(iR.name, rtS, rnS, dtAddS);


                command = iR.name +
                        " X" + String.valueOf(Integer.parseInt(String.valueOf(rt), 2)) +
                        ", [X" + String.valueOf(Integer.parseInt(String.valueOf(rn), 2)) +
                        ", #" + String.valueOf(Integer.parseInt(String.valueOf(dtAdd), 2) + "]" + ";");

            }

            if (iR.type.equals("B")) {
                char[] opcode = new char[11];
                char[] addr = new char[21];


                for (int i = 0; i < memlineBin.length; i++) {
                    if (i < 11) {
                        opcode[i] = memlineBin[i];
                    }
                    if (i > 10) {
                        addr[i - 11] = memlineBin[i];
                    }


                }

                command = iR.name  +" 0x" +String.valueOf(Integer.toHexString(Integer.parseInt(String.valueOf(addr), 2)));
                op.executeOperationB(iR.name, String.valueOf(addr));



            }

            if (iR.type.equals("CB")) {
                char[] opcode = new char[11];
                char[] addr = new char[16];
                char[] rt = new char[5];


                for (int i = 0; i < memlineBin.length; i++) {
                    if (i < 11) {
                        opcode[i] = memlineBin[i];
                    }
                    if (i > 10 && i <27 ) {
                        addr[i - 11] = memlineBin[i];
                    }
                    if (i > 26) {
                        rt[i - 27] = memlineBin[i];
                    }


                }
                command = iR.name + " X"+Integer.parseInt(String.valueOf(rt),2) +
                        " 0x" +String.valueOf(Integer.toHexString
                        (Integer.parseInt(String.valueOf(addr), 2)));

                op.executeOperationCB(iR.name, String.valueOf(addr), String.valueOf(rt));



            }

            if (iR.type.equals("S")) {
                char[] opcode = new char[11];
                char[] rn = new char[21];


                for (int i = 0; i < memlineBin.length; i++) {
                    if (i < 11) {
                        opcode[i] = memlineBin[i];
                    }
                    if (i > 10)   {
                        rn[i - 11] = memlineBin[i];
                    }



                }
                command = iR.name + " X"+Integer.parseInt(String.valueOf(rn),2)+";";

                op.executeOperationS(iR.name, String.valueOf(rn));



            }

        }
        return command;

    }


    /**
     * Converts binary string to hex string
     * @param bin the binary string
     * @return the hex string
     */
    private String binarytoHex(String bin) {
        int decimal = Integer.parseInt(bin,2);




        String hex = Integer.toString(decimal,16);
        return hex;
    }


}

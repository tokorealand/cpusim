
/**
 * The Instruction class contains representations of assembly commands.
 */

public class Instruction {
    String type;
    private int opcodeBitSize;
    private String commandString;
    String name;
    String opcode;
    String opcode2="0000";

    Instruction(String name)
    {
        this.name=name;

    }


    /**
     *
     * @param name the name of the instruction.
     * @param opcode the opcode of the instruction
     * @param type the type of the instruction
     */
   public  Instruction(String name, String opcode, String type)
    {
        this.name=name;
        this.opcode="0x" + opcode;
        if(type.equals("I")) this.opcode2 ="0x"+ Integer.toHexString(Integer.parseInt(opcode,16)+1);
        else if(type.equals("B")) this.opcode2 ="0x"+ Integer.toHexString(Integer.parseInt(opcode,16)+31);
        else if(type.equals("CB")) this.opcode2 ="0x"+ Integer.toHexString(Integer.parseInt(opcode,16)+7);
        this.type=type;
        setOpcodeBitSize();
    }

    String getName()
    {
        return name;
    }



    void setOpcodeBitSize()
    {
        if(type=="I") opcodeBitSize=10;
        if(type=="R") opcodeBitSize=11;
        if(type=="D") opcodeBitSize=11;
        if(type=="B") opcodeBitSize=11;
        if(type=="CB") opcodeBitSize=11;




    }


}

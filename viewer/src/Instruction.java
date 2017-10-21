
public class Instruction {
    String type;
    private int opcodeBitSize;
    private String commandString;
    String name;
    String opcode;
    String opcode2;

    Instruction(String name)
    {
        this.name=name;

    }

    Instruction(String name, String opcode, String type)
    {
        this.name=name;
        this.opcode="0x" + opcode;
        if(type.equals("I")) this.opcode2 ="0x"+ String.valueOf(Integer.parseInt(opcode)+1);
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



    }


}

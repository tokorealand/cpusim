import java.math.BigInteger;
import java.util.Map;


/**
 * The InstructionSet class manages all instructions and is used for checking strings for
 * any assembly commands and saving them as machine code.
 */
public class InstructionSet {
    private Instruction[] ins = new Instruction[26];
    private int current=0;
    Map<String, Integer> labels;


    /**
     * Constructor
     * @param lab the labels that were found in the assembly file
     */
    public InstructionSet(Map<String,Integer> lab)
    {
        labels=lab;
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
     * Checks to see if an Instruction is contained in the parsed line.
     * @param parsedLine the line of assembly file being parsed
     * @return string of the instruction found
     */
    String checkLineForInstruction(String parsedLine)
    {
        for (int i=0; i<ins.length; i++)
        {
            if(parsedLine.contains(ins[i].name) && !(parsedLine.contains(ins[i].name +"I"))
                    &&!(parsedLine.contains(ins[i].name+"Z"))&&!(parsedLine.contains(ins[i].name+"N"))) {

                return parseInstruction(ins[i], parsedLine);

            }
        }

        return "";
    }


    /**
     * Converts instruction to machinecode
     * @param iR the instruction we are find in the parsedline
     * @param parsedLine the line we are converting to machinecode
     * @return string of the instruction found
     */
    String parseInstruction(Instruction iR, String parsedLine)
    {
        String[] arrayLine;
        String [] command= new String[10];

        if(iR.name == "HALT") return "00000000001000000000000000000000";


        if(iR.type=="R")
        {
            if(iR.name.equals("LSL") || iR.name.equals("LSR"))
            {
                arrayLine= parsedLine.split("\\s+");
                command[0]=iR.opcode;

                command[2]=Integer.toHexString(Integer.parseInt(arrayLine[3].substring(1,arrayLine[3].length()-1)));

                command[4]="0x" + arrayLine[1].substring(1,arrayLine[1].length()-1);
                command[1]="0x0";
                command[3]="0x"+ arrayLine[2].substring(1,arrayLine[2].length()-1);


                command[0]=fromHexString(command[0],11);
                command[1]=fromHexString(command[1],5);
                command[2]=fromHexString(command[2],6);
                command[3]=fromHexString(command[3],5);
                command[4]=fromHexString(command[4],5);

                String commandString = command[0] + command[1] + command[2] + command[3] + command[4];
                return commandString;
            }
            arrayLine= parsedLine.split("\\s+");
            command[0]=iR.opcode;
            command[2]="0x0";
            command[1]="0x"+ arrayLine[3].substring(1,arrayLine[3].length()-1);
            command[3]="0x"+ arrayLine[2].substring(1,arrayLine[2].length()-1);
            command[4]="0x" + arrayLine[1].substring(1,arrayLine[1].length()-1);


            command[0]=fromHexString(command[0],11);
            command[1]=fromHexString(command[1],5);
            command[2]=fromHexString(command[2],6);
            command[3]=fromHexString(command[3],5);
            command[4]=fromHexString(command[4],5);

            String commandString = command[0] + command[1] + command[2] + command[3] + command[4];
            return commandString;
        }

        if(iR.type=="I")
        {
            arrayLine= parsedLine.split("\\s+");
            command[0]=iR.opcode;
            command[1]=Integer.toString(Integer.parseInt(arrayLine[3].substring(1,arrayLine[3].length()-1)));
            command[2]= arrayLine[2].substring(1,arrayLine[2].length()-1);
            command[3]=arrayLine[1].substring(1,arrayLine[1].length()-1);


            command[0]=fromHexString(command[0],11);
            command[0]=command[0].substring(0,10);
            command[1]=toBin(command[1],12);
            command[2]=toBin(command[2],5);
            command[3]=toBin(command[3],5);


            String commandString = command[0] + command[1] + command[2] + command[3];



            return commandString;

        }

        if(iR.type=="D") {
            arrayLine = parsedLine.split("\\s+");

            command[0] = iR.opcode;
            command[1] = arrayLine[3].substring(1, arrayLine[3].length() - 2);
            command[2] = "00";
            command[3] = arrayLine[2].substring(2, arrayLine[2].length() - 1);
            command[4] = arrayLine[1].substring(1, arrayLine[1].length() - 1);

            command[0]=fromHexString(command[0],11);
            command[1]=toBin(command[1],9);
            command[3]=toBin(command[3],5);
            command[4]=toBin(command[4],5);


            String commandString = command[0] + command[1] + command[2] + command[3] +command[4];
            return commandString;
        }

        if(iR.type.equals("B"))
        {

            arrayLine= parsedLine.split("\\s+");
            command[0]=iR.opcode;
            command[1]=(arrayLine[1].substring(0,arrayLine[1].length()-1));

            if(labels.containsKey(command[1])) command[1]=String.valueOf(labels.get(command[1]));




            command[0]=fromHexString(command[0],11);
            command[1]=fromHexString(command[1],21);



            String commandString = command[0] + command[1];
            return commandString;
        }

        if(iR.type.equals("CB"))
        {

            arrayLine= parsedLine.split("\\s+");
            command[0]=iR.opcode;
            command[1]=(arrayLine[2].substring(0,arrayLine[2].length()-1));
            command[2]=arrayLine[1].substring(1,arrayLine[1].length()-1);


            if(labels.containsKey(command[1])) command[1]=String.valueOf(labels.get(command[1]));




            command[0]=fromHexString(command[0],11);
            command[1]=fromHexString(command[1],16);
            command[2]=toBin(command[2],5);




            String commandString = command[0] + command[1] +command[2];
            return commandString;
        }

        if(iR.type.equals("S"))
        {

            arrayLine= parsedLine.split("\\s+");
            command[0]=iR.opcode;
            command[1]="0x"+(arrayLine[1].substring(1,arrayLine[1].length()-1));






            command[0]=fromHexString(command[0],11);
            command[1]=fromHexString(command[1],21);




            String commandString = command[0] + command[1];
            return commandString;
        }
        return "";

    }


    /**
     * Returns a binary string from a hex string
     * @param hex hex string
     * @param bits the number of bits wanted for the new string
     * @return the binary string
     */
    String fromHexString(String hex, int bits) {
        int hexInt = Integer.decode(hex);



        String holder = hexToBinary(hexInt);
        if(holder.length()<bits)
        {
            int padding = bits-holder.length();
            String pads="";
            for (int i=0; i<padding; i++)
            {
                pads+="0";
            }
            holder=pads+holder;

        }
        return holder;

    }

    /**
     * Converts hex string to binary string
     * @param hex the hex string
     * @return binary string
     */
    private String hexToBinary(int hex) {
        String bin = Integer.toBinaryString(hex);


        return bin;
    }


    /**
     * Converts binary string to hex string
     * @param bin the binary string
     * @return the hex string equivalent of the binary string
     */
    private String binarytoHex(String bin) {
        int decimal = Integer.parseInt(bin,2);

        String hex = Integer.toString(decimal,16);
        return hex;
    }


    /**
     * Converts int string to binary string
     * @param number the int string
     * @param bits the number of bits wanted in binary string
     * @return the binary string
     */
    private  String toBin(String number, int bits)
    {
        String binrep = Integer.toBinaryString(Integer.parseInt(number));

        if(binrep.length()>bits)
        {
            int difference = (binrep.length()-bits);
            binrep=binrep.substring(difference,binrep.length());
            return binrep;

        }

        else if(binrep.length()<bits)
        {            int difference = (bits-binrep.length());

            String sigFiller = new String(new char[difference]).replace("\0", "0");
            binrep= sigFiller+binrep;
        }





        return binrep;
    }
}


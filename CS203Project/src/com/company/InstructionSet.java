package com.company;
import java.math.BigInteger;

public class InstructionSet {
    private Instruction[] ins = new Instruction[8];
    private int current=0;

    public InstructionSet()
    {
    }

    void addInstruction(String name, String opcode, String type)
    {
        ins[current]=new Instruction( name, opcode, type);
        current++;
    }

    String checkLineForInstruction(String parsedLine)
    {
        for (int i=0; i<ins.length; i++)
        {
            if(parsedLine.contains(ins[i].name) && !(parsedLine.contains(ins[i].name +"I"))) {
                System.out.println("CHECK THUS");
                System.out.println(ins[i].name);
                return parseInstruction(ins[i], parsedLine);

            }
            }

        return "";
    }

    private Instruction checkForOp(String opcode) {
        for (int i=0; i<ins.length; i++)
        {
            if(opcode.equals(ins[i].opcode) || opcode.equals(ins[i].opcode2)) return ins[i];
        }
        return null;
    }

    String parseMemoryForInstruction(String bitCommand)
    {
        char[] memlineBin = bitCommand.toCharArray();
        char[] opcodeCheck = new char[11];
        String command="";
        for(int i = 0; i<11; i++) {
            if (i < 11) {
                opcodeCheck[i] = memlineBin[i];
            }
        }


        Instruction iR = checkForOp("0x"+binarytoHex(String.valueOf(opcodeCheck)));
        System.out.println(memlineBin);
        if (iR==null) System.out.println("0x"+String.valueOf(opcodeCheck) +"YOO");
        if(iR!=null) {

            if(iR.type.equals("R"))
            {
                char[] opcode = new char[11];
                char[] rd = new char[5];
                char[] rm =  new char[5];
                char[] shamt =  new char[6];
                char[] rn =  new char[5];

                for(int i = 0; i<memlineBin.length; i++)
                {
                    if(i<11)
                    {
                        opcode[i]=memlineBin[i];
                    }
                    if(i>10 && i<16)
                    {
                        rm[i-11]=memlineBin[i];
                    }
                    if(i>15 && i<22)
                    {
                        shamt[i-16]=memlineBin[i];
                    }
                    if(i>21 && i<27)
                    {
                        rn[i-22]=memlineBin[i];
                    }
                    if(i>26 )
                    {
                        rd[i-27]=memlineBin[i];
                    }

                }



                 command = iR.name +
                        " X" + String.valueOf(Integer.parseInt(String.valueOf(rd),2)) +
                        ", X" + String.valueOf(Integer.parseInt(String.valueOf(rn),2)) +
                        ", X" + String.valueOf(Integer.parseInt(String.valueOf(rm),2) +";");

            }

            if(iR.type.equals("I"))
            {
                char[] opcode = new char[11];
                char[] alu = new char[12];
                char[] rd =  new char[5];
                char[] rn =  new char[5];

                for(int i = 0; i<memlineBin.length; i++)
                {
                    if(i<11)
                    {
                        opcode[i]=memlineBin[i];
                    }
                    if(i>9 && i<22)
                    {
                        alu[i-10]=memlineBin[i];
                    }
                    if(i>21 && i<27)
                    {
                        rn[i-22]=memlineBin[i];
                    }
                    if(i>26 )
                    {
                        rd[i-27]=memlineBin[i];
                    }

                }
                System.out.println(String.valueOf(alu));
                System.out.println("SASASA");
                String rdS = String.valueOf(rd);
                String rnS = String.valueOf(rn);
                String aluS = String.valueOf(alu);


                if(String.valueOf(alu).length()%4 == 0 && String.valueOf(alu).substring(0,1).equals("1")  ){
                    int differnce32 =  32-String.valueOf(alu).length();
                    String sigFiller = new String(new char[differnce32]).replace("\0", "1");
                    aluS = sigFiller+String.valueOf(alu);
                    int aluI =  new BigInteger(aluS,2).intValue();
                    aluS=String.valueOf(aluI);
                    System.out.println("al");


                }



                 command = iR.name +
                        " X" + String.valueOf(Integer.parseInt(rdS,2)) +
                        ", X" + String.valueOf(Integer.parseInt(rnS,2)) +
                        ", #" + aluS +";";

            }
        }

            return command;
        }


    String parseInstruction(Instruction iR, String parsedLine)
    {
        String[] arrayLine;
        String [] command= new String[6];

        if(iR.type=="R")
        {
            arrayLine= parsedLine.split("\\s+");
            command[0]=iR.opcode;
            command[1]="0x" + arrayLine[3].substring(1,arrayLine[3].length()-1);
            command[2]="0x0";
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
            command[1]=Integer.toString(Integer.parseInt(arrayLine[3].substring(1,arrayLine[3].length()-1)),16);
            command[2]= arrayLine[2].substring(1,arrayLine[2].length()-1);
            command[3]=arrayLine[1].substring(1,arrayLine[1].length()-1);
            System.out.println(command[0]+" "+command[1]+ " " +command[2]+" " +command[3]);
            System.out.println("ADADADADA");

            command[0]=fromHexString(command[0],10);
            command[0]=command[0].substring(0,10);
            command[1]=toBin(command[1],12);
            command[2]=toBin(command[2],5);
            command[3]=toBin(command[3],5);


            String commandString = command[0] + command[1] + command[2] + command[3];
            System.out.println("here" + commandString);
            System.out.println("here" + command[0]);
            System.out.println("here" + command[1]);
            System.out.println("here" + command[2]);
            System.out.println("here" + command[3]);


            System.out.println(command[0]);
            System.out.println(iR.opcode);


            return commandString;

        }
        return "";
    }

    String fromHexString(String hex, int bits) {
        int hexInt = Integer.decode(hex);



        // System.out.println(hexInt);
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
    private String hexToBinary(int hex) {
        String bin = Integer.toBinaryString(hex);
        //System.out.println(bin.toString());


        return bin;
    }


    private String binarytoHex(String bin) {
        int decimal = Integer.parseInt(bin,2);
        System.out.println(bin);
        System.out.println("AGAIN");
        System.out.println(decimal);
        System.out.println("done");



        String hex = Integer.toString(decimal,16);
        //System.out.println(bin.toString());
        return hex;
    }

    private  String toBin(String number, int bits)
    {
        String binrep = Integer.toBinaryString(Integer.parseInt(number));

        if(binrep.length()>bits)
        {
            int difference = (binrep.length()-12);
            binrep=binrep.substring(difference,binrep.length());
            return binrep;

        }

        if(binrep.length()<bits)
        {
            String sigFiller = new String(new char[5-binrep.length()]).replace("\0", "0");
            binrep= sigFiller+binrep;
        }





        return binrep;
    }
}

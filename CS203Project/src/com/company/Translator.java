package com.company;
import java.io.*;

public class Translator {
  private  int wordSize=0;
  private  int regcnt=0;
  private  int maxmem=0x0;
  private String code ="0x8B150289";

    private Memory memMap;


    public  void main(String[] args) throws IOException {
        parseMemoryLine(code);


    }

    void readAssemblyFile(String inputFile) throws  IOException
    {
        System.out.println("FFFFFa");
        BufferedReader reader =null;
        BufferedWriter writer =null;

        try {
            reader = new BufferedReader(new FileReader("/home/tokorealand/assm.as"));
            writer = new BufferedWriter(new FileWriter("/home/tokorealand/output.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                parseAssemblyLine(line);
                writer.write(line);
            }

        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
        System.out.println(maxmem);

        System.out.println(regcnt);

        System.out.println(wordSize);

    }


    private void parseAssemblyLine(String assmLine)
    {
        String [] command= new String[6];
        String[] arrayLine;
        if(assmLine.contains(".wordsize"))
        {
            arrayLine  = assmLine.split("\\s+");
            wordSize=Integer.parseInt(arrayLine[1]);
        }
        if(assmLine.contains(".regcnt"))
        {
            arrayLine= assmLine.split("\\s+");
            regcnt=Integer.parseInt(arrayLine[1]);
        }
        if(assmLine.contains(".maxmem"))
        {
            arrayLine= assmLine.split("\\s+");
            maxmem=Integer.parseInt(fromHexString(arrayLine[1],0),2);
            memMap = new Memory(maxmem,wordSize,regcnt);
        }

        if(assmLine.contains("ADD") && !assmLine.contains("ADDI"))
        {
            arrayLine= assmLine.split("\\s+");
            command[0]="0x458";
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
            System.out.println(commandString);


            memMap.addInstructionToMemory(commandString);

            parseMemoryLine(commandString);


        }

        if(assmLine.contains("SUBI"))
        {
            arrayLine= assmLine.split("\\s+");
            command[0]="0x658";
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
            memMap.addInstructionToMemory(commandString);

            memMap.printMap();


            parseMemoryLine(commandString);


        }

        if(assmLine.contains("SUBI"))
        {
            arrayLine= assmLine.split("\\s+");
            command[0]="0x658";
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
            memMap.addInstructionToMemory(commandString);

            memMap.printMap();


            parseMemoryLine(commandString);


        }


    }





    void parseMemoryLine(String memline)
    {
        char[] memlineBin = memline.toCharArray();
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

        if(("0x"+ binarytoHex(String.valueOf(opcode))).equals("0x458"))
        {


            String command = "ADD " +
                    "X" + String.valueOf(Integer.parseInt(String.valueOf(rd),2)) +
                    ", X" + String.valueOf(Integer.parseInt(String.valueOf(rn),2)) +
                    ", X" + String.valueOf(Integer.parseInt(String.valueOf(rm),2) +";");
                    System.out.println(command);
        }

        if(("0x"+ binarytoHex(String.valueOf(opcode))).equals("0x658"))
        {

            String command = "SUBI " +
                    "X" + String.valueOf(Integer.parseInt(String.valueOf(rd),2)) +
                    ", X" + String.valueOf(Integer.parseInt(String.valueOf(rn),2)) +
                    ", #" + String.valueOf(Integer.parseInt(String.valueOf(rm),2) +";");
            System.out.println(command);
        }



        if(String.valueOf(opcode) .equals( fromHexString("0x458",0))) System.out.println("ayo");

    }


    String fromHexString(String hex, int bits) {
        int hexInt = Long.decode(hex).intValue();

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

    public int fromBinaryToInt(String bin) {
        int binInt = Integer.parseInt(bin,2);

        // System.out.println(hexInt);
        return binInt;
    }

    private String hexToBinary(int hex) {
        String bin = Integer.toBinaryString(hex);
        //System.out.println(bin.toString());
        return bin;
    }

    private String binarytoHex(String bin) {
        int decimal = Integer.parseInt(bin,2);
        String hex = Integer.toString(decimal,16);
        //System.out.println(bin.toString());
        return hex;
    }
}

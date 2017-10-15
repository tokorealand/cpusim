package com.company;
import java.io.*;

public class Translator {
  private  int wordSize=0;
  private  int regcnt=0;
  private  int maxmem=0x0;
  private String code ="0x8B150289";
  private InstructionSet ins = new InstructionSet();



    private Memory memMap;


    public  void main(String[] args) throws IOException {


    }

    void setInstructions()
    {
        ins.addInstruction("ADD","458","R");
        ins.addInstruction("ADDI","488","I");
        ins.addInstruction("ADDS","558","R");
        ins.addInstruction("ADDIS","588","I");


        ins.addInstruction("SUB","658","R");
        ins.addInstruction("SUBI","688","I");
        ins.addInstruction("SUBS","758","R");
        ins.addInstruction("SUBIS","788","I");

    }

    void readAssemblyFile(String inputFile) throws  IOException
    {
        setInstructions();
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
            memMap.printMap();

        }
        finally {
            parseMemoryLine();
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

        String lineIns= ins.checkLineForInstruction(assmLine);


        if(lineIns!="")
        {
            memMap.addInstructionToMemory(lineIns);

        }



    }





    void parseMemoryLine()
    {
        int currentByte=0;
        while(currentByte+4<maxmem) {
            String commandLine = memMap.retriveInstruction(currentByte);
            String command = ins.parseMemoryForInstruction(commandLine);
            System.out.println("SHOUDL ");
            if(command!="") System.out.println(command+"cool");
            currentByte+=4;
        }


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

    public String retreiveMemImage()
    {
        return memMap.stringMap();
    }

    public Cpu sendCpu()
    {
      return new Cpu(ins,memMap);
    }
}

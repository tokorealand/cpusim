package com.company;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class Translator {
  private  int wordSize=0;
  private  int regcnt=0;
  private  int maxmem=0x0;
  private  int currentPos=0;
  private  String stackLocation="0x0";
  private InstructionSet ins;
  private Memory memMap;
  private Cpu cpu;


    public  void main(String[] args) throws IOException {


    }

    void setInstructions()
    {
        ins.addInstruction("HALT","1","R");


        ins.addInstruction("ADD","458","R");
        ins.addInstruction("ADDI","488","I");
        ins.addInstruction("ADDS","558","R");
        ins.addInstruction("ADDIS","588","I");


        ins.addInstruction("SUB","658","R");
        ins.addInstruction("SUBI","688","I");
        ins.addInstruction("SUBS","758","R");
        ins.addInstruction("SUBIS","788","I");

        ins.addInstruction("LDUR","7c2","D");
        ins.addInstruction("STUR","7c0","D");
        ins.addInstruction("LDURSW","5c4","D");


        ins.addInstruction("AND","450","R");
        ins.addInstruction("ANDI","490","I");


        ins.addInstruction("ORR","550","R");
        ins.addInstruction("ORRI","590","I");

        ins.addInstruction("EOR","650","R");
        ins.addInstruction("EORI","690","I");

        ins.addInstruction("LSL","69b","R");
        ins.addInstruction("LSR","69a","R");








    }

    void readAssemblyFile(String inputFile) throws  IOException
    {
        ins = new InstructionSet();
        setInstructions();
        BufferedReader reader =null;



        try {
            reader = new BufferedReader(new FileReader("/home/tokorealand/output.txt"));


            String line;

            while ((line = reader.readLine()) != null) {
                parseMem(line);

            }
            cpu=new Cpu(ins,memMap,regcnt);

            memMap.printMap();

          //  parseForInstruction();

        }
        finally {
            if (reader != null) {
                reader.close();
            }

        }


    }



    private void parseMem(String memLine) {
        String[] command = new String[6];
        String[] arrayLine;

        if(memLine.contains("WS"))
        {
            arrayLine=memLine.split(":");
            wordSize=Integer.parseInt(arrayLine[0].substring(3,arrayLine[0].length()));
            maxmem=Integer.parseInt(arrayLine[1].substring(3,arrayLine[1].length()));
            regcnt=Integer.parseInt(arrayLine[2].substring(3,arrayLine[2].length()));
            stackLocation=arrayLine[3].substring(3,arrayLine[3].length());
            memMap=new Memory(maxmem,wordSize,stackLocation);
            return;

        }

        arrayLine=memLine.split("\\|");

        if(arrayLine[0].contains("0x"))
        {
            memMap.addToMemory(arrayLine[1]);
            memMap.addToMemory(arrayLine[2]);
            memMap.addToMemory(arrayLine[3]);
            memMap.addToMemory(arrayLine[4]);
        }

    }



    void parseForInstruction()
    {
        int currentByte=0;
        while(currentByte+4<maxmem) {
            String commandLine = memMap.retriveWord(currentByte);
            String command = ins.parseMemoryForInstruction(commandLine);
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
      return new Cpu(ins,memMap,regcnt);
    }
}

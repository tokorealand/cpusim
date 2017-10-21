package com.company;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class Translator {
    private  int wordSize=0;
    private  int regcnt=0;
    private  int maxmem=0x0;
    private  int currentPos=0;
    private  String stackLocation;
    Map<String, Integer> labels = new HashMap<String, Integer>();
    private InstructionSet ins;
    private Memory memMap;


    public static void main(String[] args) throws IOException {

        Translator trans = new Translator();
        trans.readAssemblyFile("S");
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
        ins = new InstructionSet(labels);
        setInstructions();
        BufferedReader reader =null;
        BufferedReader labelReader =null;

        BufferedWriter writer =null;

        try {
            reader = new BufferedReader(new FileReader("/home/tokorealand/assm.as"));
            labelReader = new BufferedReader(new FileReader("/home/tokorealand/assm.as"));
            writer = new BufferedWriter(new FileWriter("/home/tokorealand/output.txt"));

            String line;
            while ((line = labelReader.readLine()) != null) {
                parseLabels(line);
            }
            while ((line = reader.readLine()) != null) {
                parseAssemblyLine(line);
            }
            writer.write(memMap.stringMap());

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


    }


    private void parseLabels(String assmLine)
    {
        String[] arrayLine;
        if(assmLine.contains(":"))
        {
            String[] posLine= assmLine.split(":");
            labels.put(posLine[0],currentPos);

        }
        else if(assmLine.contains(".pos"))
        {
            arrayLine= assmLine.split("\\s+");
            System.out.println(arrayLine[0] + arrayLine[1]);
            String[] posLine= assmLine.split("x");

            currentPos=Integer.parseInt(posLine[1],16);
        }

        else if(assmLine.contains(".align"))
        {
            arrayLine= assmLine.split("\\s+");
            alignToBoundary(Integer.parseInt(arrayLine[1]));

        }
    }

    private void alignToBoundary(int boundary)
    {
        for(int i=0; i<maxmem-currentPos; i++){
            if((currentPos+i)%boundary==0)
            {
                currentPos=currentPos+i;
            }
        }
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
        else if(assmLine.contains(".regcnt"))
        {
            arrayLine= assmLine.split("\\s+");
            regcnt=Integer.parseInt(arrayLine[1]);
        }
        else if(assmLine.contains(".maxmem"))
        {
            arrayLine= assmLine.split("\\s+");
            maxmem=Integer.parseInt(fromHexString(arrayLine[1],0),2);
            memMap = new Memory(maxmem,wordSize,regcnt);
        }

        else if(assmLine.contains(".pos"))
        {
            arrayLine= assmLine.split("\\s+");
            System.out.println(arrayLine[0] + arrayLine[1]);
            String[] posLine= assmLine.split("x");

            memMap.setCurrentIndex(Integer.parseInt(posLine[1],16));
        }

        else if(assmLine.contains(".align"))
        {
            arrayLine= assmLine.split("\\s+");

            memMap.alignToBoundary(Integer.parseInt(arrayLine[1]));
        }
        else if(assmLine.contains(".double"))
        {
            System.out.println("CHECK");
            arrayLine= assmLine.split("\\s+");
            System.out.println(arrayLine[1]);
            System.out.println(fromHexString(arrayLine[1],0));

            memMap.addToBoundary(8,fromHexString(arrayLine[1],0));
        }
        else if(assmLine.contains(".single"))
        {
            arrayLine= assmLine.split("\\s+");
            System.out.println("single");
            memMap.addToBoundary(4,fromHexString(arrayLine[1],0));

        }
        else if(assmLine.contains(".half"))
        {
            arrayLine= assmLine.split("\\s+");
            memMap.addToBoundary(2,fromHexString(arrayLine[1],0));

        }
        else if(assmLine.contains(".byte"))
        {
            arrayLine= assmLine.split("\\s+");
            memMap.addToBoundary(1,fromHexString(arrayLine[1],0));

        }
        else if(assmLine.contains("stack"))
        {
            stackLocation="0x"+Integer.toHexString(labels.get("stack"));
            labels.remove("stack");
            memMap.updataStack(stackLocation);

        }

        else {
            String lineIns = ins.checkLineForInstruction(assmLine);


            if (lineIns != "") {
                memMap.addInstructionToMemory(lineIns);

            }
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

}

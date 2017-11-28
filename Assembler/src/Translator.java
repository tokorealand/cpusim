import java.io.*;
import java.util.Map;
import java.util.HashMap;


/**
 * Controls the parsing of the assembly file and writing to files.
 */
public class Translator {
    private String filePath;
    private String fileName;
    private  int wordSize=0;
    private  int regcnt=0;
    private  int maxmem=0x0;
    private  int currentPos=0;
    private  String stackLocation;
    Map<String, Integer> labels = new HashMap<String, Integer>();
    private InstructionSet ins;
    private Memory memMap;


    /**
     * Constructor for the Translator class.
     * @param filePath the path to the file ie. /home/user/folder
     * @param fileName the name of the file ie. code.txt, code.o

     */
    public Translator(String filePath, String fileName)
    {

        this.filePath=filePath;
        this.fileName=fileName;
    }



    /**
     * Sets the record of assembly Instructions that the program can work with.
     */
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

        ins.addInstruction("BL", "4a0","B");
        ins.addInstruction("B", "0a0","B");

        ins.addInstruction("CBNZ", "5a8","CB");
        ins.addInstruction("CBZ", "5a0","CB");

        ins.addInstruction("PUSH", "11","S");
        ins.addInstruction("POP", "21","S");








    }


    /**
     * Reads and calls the parse method on the assembly file.
     * Writes to memory image when finished.
     * @throws IOException if reading fails
     */
   public void readAssemblyFile() throws  IOException
    {
        ins = new InstructionSet(labels);
        setInstructions();
        BufferedReader reader =null;
        BufferedReader labelReader =null;

        BufferedWriter writer =null;

        try {
            reader = new BufferedReader(new FileReader(filePath+fileName));
            labelReader = new BufferedReader(new FileReader(filePath+fileName));
            writer = new BufferedWriter(new FileWriter(filePath+fileName.substring(0,fileName.length()-2)+"o"));

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
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }


    }


    /**
     * Searches for labels in the file and
     * records there posistion.
     * @param assmLine the line being parsed
     */
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


    /**
     * Aligns currentPos to something divisable by the boundary number.
     * @param boundary the number you want to mod by.
     */
    private void alignToBoundary(int boundary)
    {
        for(int i=0; i<maxmem-currentPos; i++){
            if((currentPos+i)%boundary==0)
            {
                currentPos=currentPos+i;
            }
        }
    }



    /**
     * Parses the given line for assembly commands or simulator parameters.
     * @param assmLine the line being parsed
     */
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
            arrayLine= assmLine.split("\\s+");


            memMap.addToBoundary(8,fromHexString(arrayLine[1],0));
        }
        else if(assmLine.contains(".single"))
        {
            arrayLine= assmLine.split("\\s+");
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
            memMap.updateStack(stackLocation);

        }

        else {
            String lineIns = ins.checkLineForInstruction(assmLine);


            if (lineIns != "") {
                memMap.addInstructionToMemory(lineIns);

            }
        }



    }


    /**
     * Converts hex string to binary string with padding
     * @param hex hex string
     * @param bits amount of bits needed in binary string
     * @return the binary string
     */
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


    /**
     * Converts hex to binary with no padding
     * @param hex hex string
     * @return binary string
     */
    private String hexToBinary(int hex) {
        String bin = Integer.toBinaryString(hex);
        //System.out.println(bin.toString());
        return bin;
    }



    /**
     * Gets a string representation of the memory.
     * @return a string representation of the memory.
     */
    public String retreiveMemImage()
    {
        return memMap.stringMap();
    }

}

package com.company;
import java.io.*;

/**
 * Controls the parsing of the memory image and writing to files.
 */
public class Translator {
  public static String  noisy; // check to see whether I should print out certain things
  public static String hex;    // check to see what the memory image should be displayed in
  private String filePath;
  private String fileName;
  private  int wordSize=0;
  private  int regcnt=0;
  private  int maxmem=0x0;
  private  int currentPos=0;
  private  String stackLocation="0x0";
  private InstructionSet ins;
  private Memory memMap;
  private Cpu cpu;


    /**
     * Constructor for the Translator class.
     * @param filePath the path to the file ie. /home/user/folder
     * @param fileName the name of the file ie. code.txt, code.o
     * @param noisy    tells me whether I should print register changes
     * @param hex      tells me how to display memory
     */
    public Translator(String filePath, String fileName,String noisy, String hex)
    {
        this.noisy=noisy;
        this.hex=hex;
        this.filePath=filePath;
        this.fileName=fileName;
    }

    /**
     * Writes the memory and simulation to two separate files.
     * The two files are mem"file" and sim"file"
     * @throws IOException
     */
    public void save() throws IOException
    {
        BufferedWriter writerM =null;
        BufferedWriter writerS =null;

        try {
            writerM = new BufferedWriter(new FileWriter(filePath+"mem"+fileName));
            writerS = new BufferedWriter(new FileWriter(filePath+"sim"+fileName));


            if(hex.equals("false")) {
                writerM.write(memMap.stringMap());
            }
            else{
                writerM.write(memMap.stringMapHex());
            }
           writerS.write("Registers:"+"\n");
           writerS.write(cpu.getRegisters());
            writerS.write("SP: "+cpu.getStack()+"\n");
            writerS.write("LR: "+cpu.getLinkRegister()+"\n");
            writerS.write("Flags: "+"\n");
            writerS.write(cpu.getFlags());
            writerS.write("PC: " +cpu.getCurrentLocation()+"\n");




        }
        finally {
            if (writerM != null) {
                writerM.close();
            }
            if (writerS != null) {
                writerS.close();
            }

        }
    }

    /**
     * Resets the memory to original state before the simulator started
     */
    public void reset()
    {
        memMap.reset();
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

        ins.addInstruction("B", "0a0","B");
        ins.addInstruction("BL", "4a0","B");

        ins.addInstruction("CBNZ", "5a8","CB");
        ins.addInstruction("CBZ", "5a0","CB");

        ins.addInstruction("PUSH", "11","S");
        ins.addInstruction("POP", "21","S");








    }

    /**
     * Reads and calls the parse method on the memory image.
     * Sets the memory back up when finished.
     * Creates the Cpu when finished.
     * @param inputFile the memory image being read
     * @throws IOException
     */
    void readMemFile(String inputFile) throws  IOException
    {
        ins = new InstructionSet();
        setInstructions();
        BufferedReader reader =null;



        try {
            reader = new BufferedReader(new FileReader(filePath+fileName));


            String line;

            while ((line = reader.readLine()) != null) {
                parseMem(line);

            }
            memMap.setMemoryBackUp();
            cpu=new Cpu(ins,memMap,regcnt);



        }
        finally {
            if (reader != null) {
                reader.close();
            }

        }


    }


    /**
     * Parses the given line for assembly commands or simulator parameters.
     * @param memLine the line being parsed
     */
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


    /**
     * Gets a string representation of the memory.
     * @return a string representation of the memory.
     */
    public String retreiveMemImage()
    {
        if(hex.equals("false"))
        return memMap.stringMap();
        else return memMap.stringMapHex();
    }

    public Cpu sendCpu()
    {
      return new Cpu(ins,memMap,regcnt);
    }
}

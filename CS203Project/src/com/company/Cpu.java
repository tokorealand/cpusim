package com.company;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * Cpu class manages the fetch and execute cycle and everything necessary for managing that correctly ie. memory, registers, flags, and instructions.
 */
public class Cpu {
  private   InstructionSet instructionSet;
  private   Memory memory;
  private   Register reg;
  private   String stack="0x0"; //stack address
  private   int currentByte=0;
  private   String currentInstruction="";
  private   String currentBinaryInstruction="";
  private   int[] flags = {0,0,0,0}; //pos 0-zero 1-negative 2-carry 3-overflow


    /**
     *
     * @param iSA InstructionSet with all the instructions and their parsing methods
     * @param mem The main memory.
     * @param registerCount The number of registers the cpu will have access to.
     */
    public Cpu(InstructionSet iSA, Memory mem, int registerCount)
    {
        instructionSet=iSA;
        memory=mem;
        stack=mem.getStackPointer();
        reg=new Register(registerCount);
        instructionSet.establishOperator(reg,flags,memory);



    }

    /**
     *
     * @return stack
     */
    public String getStack()
    {
        return stack;
    }

    /**
     *
     * @return maxmem
     */
    public int getMaxMem(){return memory.getMaxSize();}
    public String getLinkRegister(){return memory.getLinkPointer();}

    /**
     * Resets the memory location back to the start and clears the instruction register.
     */
    public void reset()
    {
        memory.setCurrentIndex(0);
        currentInstruction="";
    }

    /**
     * Sets the current byte in the cpu.
     * @param newByteLocation the location of the byte
     */
    public void setCurrentByte(int newByteLocation){currentByte=newByteLocation;}


    /**
     *
     * @return currentInstruction
     */
    String getCurrentInstruction()
    {
        return currentInstruction;
    }

    /**
     *
     * @return currentBinaryInstruction
     */
    String getCurrentBinaryInstruction()
    {
        return currentBinaryInstruction;
    }

    /**
     *
     * @return current memory location
     */
    String getCurrentLocation()
    {
        String hex="0x"+Integer.toHexString(memory.getCurrentIndex());
        return hex;
    }

    /**
     *
     * @return String containing all the registers and their values.
     */
    String getRegisters()
    {
        String[] regC=reg.getRegisters();
        String stringRegs="";
       for(int i=0; i<regC.length; i++)
       {

           stringRegs+="X"+String.valueOf(i)+" = "+String.valueOf(new BigInteger(regC[i],2).intValue()) +"\n";
       }
       return stringRegs;
    }

    /**
     *
     * @return String containing all the flags and their values.
     */
    public String getFlags()
    {
        String flagString="";
        for(int i=0; i<flags.length; i++)
        {
            if(i==0) flagString+="Z = "+flags[i] +"\n";
            if(i==1) flagString+="N = "+flags[i] +"\n";
            if(i==2) flagString+="C = "+flags[i] +"\n";
            if(i==3) flagString+="V = "+flags[i] +"\n";

        }
        return flagString;
    }


    /**
     *
     * @param i index of flag being requested
     * @return requested flag
     */
    public String getRequestedFlag(int i)
    {
        return String.valueOf(flags[i]);
    }

    /**
     * Sets all the flags to false.
     */
    public void clearFlags()
    {
        flags[0]=0;
        flags[1]=0;
        flags[2]=0;
        flags[3]=0;
    }

    /**
     * Fetches the next instruction in memory and executes it. Increments index of main memory.
     * Updates stack pointer if necessary.
     */
    void fetchAndExecute()
    {
        clearFlags();
        if(memory.getCurrentIndex()+4<memory.getMaxSize()) {
             currentBinaryInstruction = memory.retriveWord(memory.getCurrentIndex());
            currentInstruction =instructionSet.parseMemoryForInstruction(currentBinaryInstruction);
        }
        stack= memory.getStackPointer();
    }


}

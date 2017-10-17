package com.company;

public class Cpu {
  private   InstructionSet instructionSet;
  private   Memory memory;
  private   Register reg;
  private   int currentByte=0;
  private   String currentInstruction="";
  private   String currentBinaryInstruction="";
  private   boolean[] flags = new boolean[4];


    public Cpu(InstructionSet iSA, Memory mem, int registerCount)
    {
        instructionSet=iSA;
        memory=mem;
        reg=new Register(registerCount);
        getNextInstruction();
        initiateFlags();



    }

    void initiateFlags()
    {
        for(boolean flag : flags)
        {
            flag=false;
        }
    }





    String getCurrentInstruction()
    {
        return currentInstruction;
    }

    String getCurrentBinaryInstruction()
    {
        return currentBinaryInstruction;
    }

    String getCurrentLocation()
    {
        String hex="0x"+Integer.toHexString(currentByte);
        return hex;
    }

    String getRegisters()
    {
        int[] regC=reg.getRegisters();
        String stringRegs="";
       for(int i=0; i<regC.length; i++)
       {
           stringRegs+="X"+String.valueOf(i)+" = "+String.valueOf(regC[i]) +"\n";
       }
       return stringRegs;
    }
    void getNextInstruction()
    {
        if(currentByte+4<memory.getMaxSize()) {
             currentBinaryInstruction = memory.retriveInstruction(currentByte);
            currentInstruction =instructionSet.parseMemoryForInstruction(currentBinaryInstruction);
            currentByte+=4;
        }
    }
}

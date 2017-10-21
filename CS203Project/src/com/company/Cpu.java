package com.company;
import java.math.BigInteger;


public class Cpu {
  private   InstructionSet instructionSet;
  private   Memory memory;
  private   Register reg;
  private   String stack="0x0";
  private   int currentByte=0;
  private   String currentInstruction="";
  private   String currentBinaryInstruction="";
  private   int[] flags = {0,0,0,0}; //pos 0-zero 1-negative 2-carry 3-overflow


    public Cpu(InstructionSet iSA, Memory mem, int registerCount)
    {
        instructionSet=iSA;
        memory=mem;
        stack=mem.getStackPointer();
        reg=new Register(registerCount);
        instructionSet.establishOperator(reg,flags,memory);
        reg.print();
        System.out.println("SSSSSSSSss");
        memory.printMap();



    }

    public String getStack()
    {
        return stack;
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
        String[] regC=reg.getRegisters();
        String stringRegs="";
       for(int i=0; i<regC.length; i++)
       {
           System.out.println(regC[i]);
           System.out.println(new BigInteger(regC[i],2).intValue());
           System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSS");
           stringRegs+="X"+String.valueOf(i)+" = "+String.valueOf(new BigInteger(regC[i],2).intValue()) +"\n";
       }
       System.out.println(stringRegs);
       return stringRegs;
    }



    public String getRequestedFlag(int i)
    {
        return String.valueOf(flags[i]);
    }

    public void clearFlags()
    {
        flags[0]=0;
        flags[1]=0;
        flags[2]=0;
        flags[3]=0;
    }

    void fetchAndExecute()
    {
        clearFlags();
        if(currentByte+4<memory.getMaxSize()) {
             currentBinaryInstruction = memory.retriveWord(currentByte);
            currentInstruction =instructionSet.parseMemoryForInstruction(currentBinaryInstruction);
            currentByte+=4;
        }
    }
}

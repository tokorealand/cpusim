package com.company;

public class Cpu {
    InstructionSet instructionSet;
    Memory memory;
    int currentByte=0;
    String currentInstruction="";
    String currentBinaryInstruction="";

    public Cpu(InstructionSet iSA, Memory mem)
    {
        instructionSet=iSA;
        memory=mem;
        getNextInstruction();



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

    void getNextInstruction()
    {
        if(currentByte+4<memory.getMaxSize()) {
             currentBinaryInstruction = memory.retriveInstruction(currentByte);
            currentInstruction =instructionSet.parseMemoryForInstruction(currentBinaryInstruction);
            currentByte+=4;
        }
    }
}

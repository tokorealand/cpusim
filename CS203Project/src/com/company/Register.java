package com.company;

public class Register {
    int[] registers;


    public Register(int numberRegister)
    {
        registers=new int[numberRegister];
        for (int reg:registers) {
            reg=0;

        }
    }

    public int getRegister(int registerNumber) {


        return registers[registerNumber];

    }

    public void setRegisters(int registerNumber, int value)
    {
        registers[registerNumber] = value;
    }

    public int[] getRegisters() {
        return registers;
    }
}

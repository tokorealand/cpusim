package com.company;

public class Register {
    String[] registers;


    public Register(int numberRegister)
    {
        registers=new String[numberRegister];
        for(int i=0; i<registers.length; i++)
        {
            registers[i]="00000000000000000000000000000000";
        }

    }

    public  void print()
    {
        for (String reg:registers) {
        System.out.println(reg);
        }
    }

    public String getRegister(int registerNumber) {


        return registers[registerNumber];

    }

    public void setRegisters(int registerNumber, String value)
    {
        registers[registerNumber] = value;
    }

    public String[] getRegisters() {
        return registers;
    }
}

package com.company;

public class Byte {
    private int decimal;
    private String binary;
    private String hex;


    /**
     * Constructors for objects of class Byte
     */


    Byte() {
        decimal = 0;
        binary = "00000000";
        hex= "0xff";

    }

    public Byte(String bin)
    {
        binary=bin;
        decimal=Integer.parseInt(bin,2);
        hex="0x"+Integer.parseInt(bin,16);
    }





    public void setDecimal(int dNum) {
        if (dNum < 127 & dNum > -128) {
            decimal = dNum;
            binary = Integer.toString(decimal, 2);
        }
    }

    void setBinary(String bSeq) {
            binary = bSeq;
            decimal = Integer.parseInt(bSeq, 2);


    }

    public int getDecimal() {
        return this.decimal;
    }

    String getBinary() {
        return this.binary;
    }
}

package com.company;

public class Byte {
    private int decimal;
    private String binary;

    /**
     * Constructors for objects of class Byte
     */


    Byte() {
        decimal = 0;
        binary = "00000000";
    }

    public Byte(int dNum, String bSeq) {
        if (dNum < 127 & dNum > -128) {
            decimal = dNum;
            binary = bSeq;
        }
    }


    public Byte(String bSeq) {
        if (Integer.parseInt(bSeq, 2) < 127 & Integer.parseInt(bSeq, 2) > -128) {
            binary = bSeq;
            decimal = Integer.parseInt(bSeq, 2);

        }
    }


    public Byte(int dNum) {
        if (dNum < 127 & dNum > -128) {
            decimal = dNum;
            binary = Integer.toString(decimal, 2);
        }

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

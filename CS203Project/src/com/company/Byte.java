package com.company;


/**
 * The Byte class stores byte addressable memory in binary, and decimal
 */
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
        hex= "0x00000000";

    }

    /**
     * Copy Constructor
     * @param b is a Byte object
     */
    public Byte(Byte b)
    {
        binary=b.getBinary();
        hex=b.getHex();
    }

    public Byte(String bin)
    {
        binary=bin;
        decimal=Integer.parseInt(bin,2);
        hex="0x"+padHex(Integer.toHexString(Integer.parseInt(bin,2)));
    }


    /**
     *
     * @param bSeq Binary String
     */
    void setBinary(String bSeq) {
            binary = bSeq;
            decimal = Integer.parseInt(bSeq, 2);


    }

    /**
     * Pads the hex contained to be a full byte.
     * @param hex String
     * @return a padded hex string
     */
    String padHex(String hex)
    {

        String padding = new String(new char[2-hex.length()]).replace("\0", "0");
        hex=padding+hex;
        return hex;
    }

    public int getDecimal() {
        return this.decimal;
    }

    /**
     *
     * @return binary
     */
    String getBinary() {
        return this.binary;
    }

    /**
     *
     * @return hex
     */
    String getHex() {
        return this.hex;
    }

}

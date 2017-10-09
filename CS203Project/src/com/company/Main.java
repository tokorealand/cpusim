package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Translator t = new Translator();
        System.out.println("DDDDD");
        t.parseMemoryLine(t.fromHexString("0x8B150289",0));

        t.readAssemblyFile("D");

    }
}

package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Translator t = new Translator();
        System.out.println("DDDDD");

        t.readAssemblyFile("D");
        t.parseMemoryLine();

        Gui gu = new Gui(t);

    }
}

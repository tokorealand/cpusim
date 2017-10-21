package com.company;

import java.util.ArrayList;

class Memory {

    private ArrayList<Byte> memory = new ArrayList<Byte>();

    private int currentIndex=0;
    private int wordSize;
    private int maxSize;
    private String stackPointer;


    public static void main(String args[])
    {

    }

        Memory(int maxSize, int wordSize, String stack)
        {
            this.maxSize=maxSize;
            this.wordSize=wordSize;
            this.stackPointer=stack;


        }

        public void storeWord(int location, String word)
        {
            int holder=currentIndex;
            currentIndex=location;
            setWordInMemory(word);
            currentIndex=holder;
        }

    void setWordInMemory(String command)
    {
        System.out.println(command);
        System.out.println("COMMADE");
        String parser="";
        char[] com = command.toCharArray();

        for(int i=0; i<command.length(); i++){
            parser+=com[i];
            if((i+1)%8==0)
            {
                setByte(parser);
                parser="";

            }
        }

    }

        String retriveWord(int byteNum)
        {
            String bitCommand="";
            System.out.println("QQQQ");
            System.out.println(byteNum);
                for (int i = 0; i < 4; i++) {
                    bitCommand += memory.get(byteNum + i).getBinary();

            }
            return bitCommand;
        }

    public void addToMemory(String bin)
    {

        memory.add(new Byte(bin));


    }

    public void setByte(String bin)
    {
      memory.get(currentIndex).setBinary(bin);
      currentIndex++;

    }

    public String getStackPointer()
    {
        return stackPointer;
    }



        void addInstructionToMemory(String command)
        {
            System.out.println(command);
            System.out.println("COMMADE");
            String parser="";
            char[] com = command.toCharArray();

            for(int i=0; i<command.length(); i++){
                parser+=com[i];
                if((i+1)%8==0)
                {
                    addToMemory(parser);
                    parser="";

                }
            }

        }

        public void setCurrentIndex(int index)
        {
            currentIndex=index;
        }

    void addData(String data)
    {
        String parser="";
        char[] dats = data.toCharArray();


            for (int i = 0; i < data.length(); i++) {
                parser += dats[i];
                if ((i + 1) % 8 == 0) {
                    addToMemory(parser);
                    parser = "";

                }
            }
        }









        void printMap()
        {
            System.out.println("WS-"+Integer.toString(wordSize)+":MM-"+Integer.toString(maxSize) +":SP-"+stackPointer);
            String border = new String(new char[(wordSize/8)*10]).replace("\0", "-");

            for (int i=0; i<memory.size(); i++)
            {
                if(i%(wordSize/8)==0)
                {
                    System.out.println(" ");
                    System.out.println(border);
                    System.out.print("0x"+Integer.toHexString(i));
                    System.out.print("|");
                }

                    System.out.print(memory.get(i).getBinary());
                    System.out.print("|");


            }
            System.out.println(" ");
            System.out.println(border);

        }


    String stringMap()
    {
        String map = "WS-"+Integer.toString(wordSize)+":MM-"+Integer.toString(maxSize) +":SP-"+stackPointer;
        String border = new String(new char[(wordSize/8)*20]).replace("\0", "-");

        for (int i=0; i<memory.size(); i++)
        {
            if(i%(wordSize/8)==0)
            {
                map+="\n";
                map+=border+"\n";
                map+="0x"+Integer.toHexString(i);
                map+="|";

            }

            map+=memory.get(i).getBinary();
            map+="|";


        }
        map+="\n";
        map+=border+"\n";
        return  map;

    }

    int getMaxSize()
    {
        return maxSize;
    }


}

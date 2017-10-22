package com.company;

import java.util.ArrayList;

public class Memory {

    private ArrayList<Byte> memory = new ArrayList<Byte>();
    private ArrayList<Byte> memoryBackUp = new ArrayList<Byte>();

    private int currentIndex=0;
    private int wordSize;
    private int maxSize;
    private String stackPointer="0x0";
    private String linkPointer;


    public static void main(String args[])
    {

    }


        Memory(int maxSize, int wordSize, String stack)
        {
            this.maxSize=maxSize;
            this.wordSize=wordSize;
            this.stackPointer=stack;


        }

        public void setLinkPointer(String lp) { linkPointer=lp; }

    public void pushStack(String wordPushed)
    {

        storeWord(Integer.parseInt(stackPointer.substring(2),16),wordPushed);
        stackPointer="0x"+Integer.toHexString(Integer.parseInt(stackPointer.substring(2),16)+4);
    }

    public String popStack()
    {
       String holder = retriveWord(Integer.parseInt(stackPointer.substring(2),16)-4);
        stackPointer="0x"+Integer.toHexString(Integer.parseInt(stackPointer.substring(2),16)-4);
        return holder;

    }



    public String getLinkPointer(){return linkPointer;}

        public void storeWord(int location, String word)
        {
            int holder=currentIndex;
            currentIndex=location;
            setWordInMemory(word);
            currentIndex=holder;
        }

    void setWordInMemory(String command)
    {

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

   public void setMemoryBackUp()
    {
        for (int i=0; i<memory.size(); i++)
        {
            memoryBackUp.add(new Byte(memory.get(i)));
        }

    }

    int getCurrentIndex(){return currentIndex; }

        String retriveWord(int byteNum)
        {
            String bitCommand="";

                for (int i = 0; i < 4; i++) {
                    bitCommand += memory.get(byteNum + i).getBinary();

            }
            currentIndex+=4;
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

   public void reset()
    {
        memory=memoryBackUp;

    }





    public void printHex()
    {
        System.out.println("WS-"+Integer.toString(wordSize)+":MM-"+Integer.toString(maxSize) +":SP-"+stackPointer);
        String border = new String(new char[(wordSize/8)*6]).replace("\0", "-");

        for (int i=0; i<memory.size(); i++)
        {
            if(i%(wordSize/8)==0)
            {
                System.out.println(" ");
                System.out.println(border);
                System.out.print("0x"+Integer.toHexString(i));
                System.out.print("|");
            }

            System.out.print(memoryBackUp.get(i).getHex());
            System.out.print("|");


        }
        System.out.println(" ");
        System.out.println(border);

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

    String stringMapHex()
    {
        String map = "WS-"+Integer.toString(wordSize)+":MM-"+Integer.toString(maxSize) +":SP-"+stackPointer;
        String border = new String(new char[(wordSize/8)*10]).replace("\0", "-");

        for (int i=0; i<memory.size(); i++)
        {
            if(i%(wordSize/8)==0)
            {
                map+="\n";
                map+=border+"\n";
                map+="0x"+Integer.toHexString(i);
                map+="|";

            }

            map+=memory.get(i).getHex();
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

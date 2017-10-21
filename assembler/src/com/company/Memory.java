package com.company;

class Memory {

    private String[] memMap;
    private Byte[] memory;
    private int currentIndex=0;
    private int wordSize;
    private int maxSize;
    private int registerCount;
    private String stackLocation="0x0";


    public static void main(String args[])
    {

    }

    Memory(int maxSize, int wordSize, int registerCount)
    {
        this.maxSize=maxSize;
        this.wordSize=wordSize;
        this.registerCount=registerCount;

        memory = new Byte[maxSize];
        for (int i=0; i<memory.length; i++)
        {
            memory[i]=new Byte();
        }
    }

    public void updataStack(String pointer){ this.stackLocation=pointer;}


    String retriveInstruction(int byteNum)
    {
        String bitCommand="";
        for(int i=0; i<4; i++)
        {
            bitCommand+=memory[byteNum+i].getBinary();
        }
        return bitCommand;
    }



    void addInstructionToMemory(String command)
    {
        String parser="";
        char[] com = command.toCharArray();
        System.out.println("GERE");
        System.out.println(command);
        for(int i=0; i<command.length(); i++){
            parser+=com[i];
            if((i+1)%8==0)
            {
                System.out.println(parser);
                addToMemory(parser);
                parser="";
                System.out.println("srpe");

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
        System.out.println("GERE");
        System.out.println(data);
        for(int i=0; i<data.length(); i++){
            parser+=dats[i];
            if((i+1)%8==0)
            {
                System.out.println(parser);
                addToMemory(parser);
                parser="";
                System.out.println("srpe");

            }
        }

    }


    private void addToMemory(String commandByte)
    {
        System.out.println("start");

        System.out.println(commandByte);
        System.out.println("Done");
        if(currentIndex<maxSize) {
            memory[currentIndex].setBinary(commandByte);
            currentIndex++;
        }
        else  System.out.println("MAX MEM EXCEEDED");

    }


    public void alignToBoundary(int boundary)
    {


        for(int i=0; i<memory.length-currentIndex; i++){
            if((currentIndex+i)%boundary==0)
            {
                currentIndex=currentIndex+i;
                return;
            }
        }
    }

    public void addToBoundary(int boundary,String tobeAdded)
    {
            System.out.println("AYO");
            System.out.println(tobeAdded);

        for(int i=0; i<memory.length-currentIndex; i++) {
            System.out.println("SSSg");
            System.out.println(currentIndex);


            System.out.println(i);
            if ((currentIndex + i) % boundary == 0) {
                currentIndex = currentIndex + i;
                break;

            }
        }
            System.out.println("AYO2");

            System.out.println(padBinary(tobeAdded,boundary*8));
            if(currentIndex-boundary >-1) {
                addData(padBinary(tobeAdded, boundary * 8));
            }
            else System.out.println("Align failed to find suitable location");

    }

    private  String padBinary(String binrep, int bitLimit) {


        if (binrep.length() < bitLimit ) {
            int difference = (bitLimit - binrep.length());
            String sigFiller = new String(new char[difference]).replace("\0", "0");
            binrep = sigFiller + binrep;
        }
        return binrep;
    }

    void printMap()
    {
        System.out.println("WS-"+Integer.toString(wordSize)+":MM-"+Integer.toString(maxSize) +":RC-"+Integer.toString(registerCount));
        String border = new String(new char[(wordSize/8)*10]).replace("\0", "-");

        for (int i=0; i<memory.length; i++)
        {
            if(i%(wordSize/8)==0)
            {
                System.out.println(" ");
                System.out.println(border);
                System.out.print("0x"+Integer.toHexString(i));
                System.out.print("|");
            }

            System.out.print(memory[i].getBinary());
            System.out.print("|");


        }
        System.out.println(" ");
        System.out.println(border);

    }


    String stringMap()
    {
        String map = "WS-"+Integer.toString(wordSize)+":MM-"+
                Integer.toString(maxSize) +":RC-"
                +Integer.toString(registerCount)+
                ":SP-"+stackLocation;
        String border = new String(new char[(wordSize/8)*10]).replace("\0", "-");

        for (int i=0; i<memory.length; i++)
        {
            if(i%(wordSize/8)==0)
            {
                map+="\n";
                map+=border+"\n";
                map+="0x"+Integer.toHexString(i);
                map+="|";

            }

            map+=memory[i].getBinary();
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

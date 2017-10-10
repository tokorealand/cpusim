package com.company;

class Memory {

    private String[] memMap;
  private Byte[] memory;
    private int currentIndex=0;
  private int usedMem=0;
  private int wordSize;
  private int maxSize;
  private int registerCount;

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

        void printMap()
        {
            System.out.println("WS-"+Integer.toString(wordSize)+":MM-"+Integer.toString(maxSize) +":RC-"+Integer.toString(registerCount));
            String border = new String(new char[wordSize*10]).replace("\0", "-");

            for (int i=0; i<memory.length; i++)
            {
                if(i%wordSize==0)
                {
                    System.out.println(" ");
                    System.out.println(border);
                    System.out.print("0x"+Integer.toString(i));
                    System.out.print("|");
                }

                    System.out.print(memory[i].getBinary());
                    System.out.print("|");


            }
            System.out.println(" ");
            System.out.println(border);

        }


}

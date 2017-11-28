

import java.util.ArrayList;


/**
 * Memory contains all the methods and structures to simulate computer memory.
 */
public class Memory {

    private ArrayList<Byte> memory = new ArrayList<Byte>();
    private ArrayList<Byte> memoryBackUp = new ArrayList<Byte>();

    private int currentIndex=0;
    private int wordSize;
    private int maxSize;
    private String stackPointer="0x0";
    private String linkPointer;


    /**
     * Constructor
     * @param maxSize max addressable memory
     * @param wordSize wordsize of simulator
     * @param stack the pointer to stack
     */
    public Memory(int maxSize, int wordSize, String stack)
        {
            this.maxSize=maxSize;
            this.wordSize=wordSize;
            this.stackPointer=stack;


        }

    /**
     * Sets the address of the link register.
     * @param lp the address of link register
     */
    public void setLinkPointer(String lp) { linkPointer=lp; }


    /**
     * Pushes a word onto the stack and increments the stack pointer.
     * @param wordPushed the word being inserted to stack
     */
    public void pushStack(String wordPushed)
    {

        storeWord(Integer.parseInt(stackPointer.substring(2),16),wordPushed);
        stackPointer="0x"+Integer.toHexString(Integer.parseInt(stackPointer.substring(2),16)+4);
    }


    /**
     * Pops a word from the stack and outputs the word to be stored in a register.
     * It also decrements the stack pointer.
     * @return the string to be stored in a register
     */
    public String popStack()
    {
       String holder = retriveWord(Integer.parseInt(stackPointer.substring(2),16)-4);
        stackPointer="0x"+Integer.toHexString(Integer.parseInt(stackPointer.substring(2),16)-4);
        return holder;

    }


    /**
     * Returns the address of the link register.
     * @return the address of the link register
     */
    public String getLinkPointer(){return linkPointer;}

        public void storeWord(int location, String word)
        {
            int holder=currentIndex;
            currentIndex=location;
            setWordInMemory(word);
            currentIndex=holder;
        }


    /**
     * Replaces a word in memory.
     * @param command the word that will be doing the replacing
     */
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

    /**
     * Sets the memory back up(Deep Copy) for the reset method.
     */
   public void setMemoryBackUp()
    {
        for (int i=0; i<memory.size(); i++)
        {
            memoryBackUp.add(new Byte(memory.get(i)));
        }

    }

    /**
     * Gets the current memory index
     * @return current index
     */
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

    /**
     * Adds a new byte to memory using the binary string.
     * @param bin the binary string that the byte will represent
     */
    public void addToMemory(String bin)
    {

        memory.add(new Byte(bin));


    }

    /**
     * Sets the byte at the current index with a new value and increments current index.
     * @param bin the binary string to set the byte to.
     */
    public void setByte(String bin)
    {
      memory.get(currentIndex).setBinary(bin);
      currentIndex++;

    }

    /**
     * Gets the address of the stack.
     * @return stack address
     */
    public String getStackPointer()
    {
        return stackPointer;
    }


    /**
     * Sets the current index to index.
     * @param index the index that should be set
     */
    public void setCurrentIndex(int index)
        {
            currentIndex=index;
        }


    /**
     * Sets memory back to original before the simulator started.
     */
    public void reset()
    {
        memory=memoryBackUp;

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
                    System.out.print("0x"+padHex(Integer.toHexString(i)));
                    System.out.print("|");
                }

                    System.out.print(memory.get(i).getBinary());
                    System.out.print("|");


            }
            System.out.println(" ");
            System.out.println(border);

        }

/**
     * Pads the hex contained to be a full word.
     * @param hex String
     * @return a padded hex string
     */
    String padHex(String hex)
    {

        String padding = new String(new char[8-hex.length()]).replace("\0", "0");
        hex=padding+hex;
        return hex;
    }

    /**
     * Gets a string representation of the memory map in binary.
     * @return string representation of memory.
     */
    String stringMap()
    {
        String map = "WS-"+Integer.toString(wordSize)+":MM-"+Integer.toString(maxSize) +":SP-"+stackPointer;
        String border = new String(new char[(wordSize/8)*23]).replace("\0", "-");

        for (int i=0; i<memory.size(); i++)
        {
            if(i%(wordSize/8)==0)
            {
                map+="\n";
                map+=border+"\n";
                map+="0x"+padHex(Integer.toHexString(i));
                map+="|";

            }

            map+=memory.get(i).getBinary();
            map+="|";


        }
        map+="\n";
        map+=border+"\n";
        return  map;

    }


    /**
     * Gets a string representation of the memory map in hex.
     * @return string representation of memory.
     */
    String stringMapHex()
    {
        String map = "WS-"+Integer.toString(wordSize)+":MM-"+Integer.toString(maxSize) +":SP-"+stackPointer;
        String border = new String(new char[(wordSize/8)*14]).replace("\0", "-");

        for (int i=0; i<memory.size(); i++)
        {
            if(i%(wordSize/8)==0)
            {
                map+="\n";
                map+=border+"\n";
                map+="0x"+padHex(Integer.toHexString(i));
                map+="|";

            }

            map+=memory.get(i).getHex();
            map+="|";


        }
        map+="\n";
        map+=border+"\n";
        return  map;

    }

    /**
     * Get max memory.
     * @return max memory size
     */
    int getMaxSize()
    {
        return maxSize;
    }


}


/**
 * Memory contains all the methods and structures to simulate computer memory.
 */
public class Memory {

    private String[] memMap;
    private Byte[] memory;
    private int currentIndex=0;
    private int wordSize;
    private int maxSize;
    private int registerCount;
    private String stackLocation="0x0";



    /**
     * Constructor
     * @param maxSize max addressable memory
     * @param wordSize wordsize of simulator
     * @param registerCount number of registers to bre created
     */
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

    /**
     * Sets the stack pointer.
     * @param pointer the address of the stack
     */
    public void updateStack(String pointer){ this.stackLocation=pointer;}


    /**
     * Retrieve a word from a starting byte number.
     * @param byteNum the index of the start of the Instruction
     * @return the instruction
     */
    String retriveInstruction(int byteNum)
    {
        String bitCommand="";
        for(int i=0; i<4; i++)
        {
            bitCommand+=memory[byteNum+i].getBinary();
        }
        return bitCommand;
    }


    /**
     * Adds an Instruction binary string to memory.
     * @param command the binary instruction string
     */
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

    /**
     * Sets the current index to index.
     * @param index the index that should be set
     */
    public void setCurrentIndex(int index)
    {
        currentIndex=index;
    }


    /**
     * Adds binary string to memory
     * @param data the binary string
     */
    void addData(String data)
    {
        String parser="";
        char[] dats = data.toCharArray();

        for(int i=0; i<data.length(); i++){
            parser+=dats[i];
            if((i+1)%8==0)
            {
                addToMemory(parser);
                parser="";

            }
        }

    }

    /**
     * Seta a byte's binary using the binary string.
     * @param commandByte the binary string that the byte will represent
     */
    private void addToMemory(String commandByte)
    {

        if(currentIndex<maxSize) {
            memory[currentIndex].setBinary(commandByte);
            currentIndex++;
        }
        else  System.out.println("MAX MEM EXCEEDED");

    }

    /**
     * Aligns memory address to boundary.
     * @param boundary the boundary to align to
     */
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


    /**
     * Adds a binary string to a certain boundary.
     * @param boundary the boundary to align to
     * @param tobeAdded the binary to be added
     */
    public void addToBoundary(int boundary,String tobeAdded)
    {

        for(int i=0; i<memory.length-currentIndex; i++) {

            if ((currentIndex + i) % boundary == 0) {
                currentIndex = currentIndex + i;
                break;

            }
        }

            if(currentIndex-boundary >-1) {
                addData(padBinary(tobeAdded, boundary * 8));
            }
            else System.out.println("Align failed to find suitable location");

    }


    /**
     * Pads a bianry string to the requested bitLimit.
     * @param binrep the binary string
     * @param bitLimit the requested size
     * @return a padded binary string
     */
    private  String padBinary(String binrep, int bitLimit) {
        if (binrep.length() < bitLimit ) {
            int difference = (bitLimit - binrep.length());
            String sigFiller = new String(new char[difference]).replace("\0", "0");
            binrep = sigFiller + binrep;
        }
        return binrep;
    }

/** Pads the hex contained to be a full word.
     * @param hex String
     * @return a padded hex string
     */
    String padHex(String hex)
    {

        String padding = new String(new char[8-hex.length()]).replace("\0", "0");
        hex=padding+hex;
        return hex;
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
                map+="0x"+padHex(Integer.toHexString(i));
                map+="|";

            }

            map+=memory[i].getBinary();
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

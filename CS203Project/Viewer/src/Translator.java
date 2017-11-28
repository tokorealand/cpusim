import java.io.*;

/**
 * Controls the parsing of the memory image.
 */
public class Translator {

    private  int start = 0;
    private  int end = 0;
    private Memory memMap = new Memory();
    public static String hex;    // check to see what the memory image should be displayed in
    private String filePath;
    private String fileName;


    /**
     * Constructor for the Translator class.
     * @param filePath the path to the file ie. /home/user/folder
     * @param fileName the name of the file ie. code.txt, code.o
     * @param hex      tells me how to display memory
     * @param start    the memory index to start from
     * @param end      the memory index to end at.
     */
    public Translator(String filePath, String fileName, String hex, String start, String end)
    {
        this.hex=hex;
        this.filePath=filePath;
        this.fileName=fileName;
        this.start=Integer.parseInt(start);
        this.end=Integer.parseInt(end);
    }


    /**
     * Gets a string representation of the memory.
     * @return a string representation of the memory.
     */
    public String getMap()
    {
        if(hex.equals("false")) return memMap.stringMap(start,end);
        return memMap.stringMapHex(start,end);
    }



    /**
     * Reads and calls the parse method on the memory image.
     * Sets the memory back up when finished.
     * Creates the Cpu when finished.
     * @throws IOException if reading fails
     */
   public void readMemImage() throws  IOException
    {

        BufferedReader reader =null;


        try {
            reader = new BufferedReader(new FileReader(filePath+fileName));

            String line;

            while ((line = reader.readLine()) != null) {
                parseMem(line);
            }
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }


    }




    /**
     * Parses the given line for assembly commands or simulator parameters.
     * @param memLine the line being parsed
     */
    private void parseMem(String memLine) {
        String[] command = new String[6];
        String[] arrayLine;

        arrayLine=memLine.split("\\|");



        if(arrayLine[0].contains("0x") && !arrayLine[0].contains("WS"))
        {
            memMap.addToMemory(arrayLine[1]);
            memMap.addToMemory(arrayLine[2]);
            memMap.addToMemory(arrayLine[3]);
            memMap.addToMemory(arrayLine[4]);
        }

    }













}

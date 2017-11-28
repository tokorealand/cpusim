import java.util.ArrayList;
/**
 * Memory contains all the methods and structures to simulate computer memory.
 */
public class Memory {

    private ArrayList<Byte> memory = new ArrayList<Byte>();
    private int currentIndex=0;



    public Memory()
    {

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
     * Gets a string representation of the memory map in binary.
     * @param start the string starts from this byte location
     * @param end the string ends at this byte location
     * @return string representation of memory.
     */
    String stringMap(int start, int end) {
        String mapS = "";

        if (start > -1 && end < memory.size()) {

            for (int i = start; i < end+1; i++)
                {

                  mapS += "0x"+padHex(Integer.toHexString(i) +" ");
                  mapS += memory.get(i).getBinary()+ "\n";

                }
                return mapS;

            }

        System.out.println("YOUR RANGE IS ILLEGAL!");
        return "";

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

    /**
     * Gets a string representation of the memory map in hex.
     * @param start the string starts from this byte location
     * @param end the string ends at this byte location
     * @return string representation of memory.
     */
    String stringMapHex(int start, int end) {
        String mapS = "";

        if (start > -1 && memory.size() < memory.size() + 1) {

            for (int i = start; i < end+1; i++)
            {

                mapS += "0x"+padHex(Integer.toHexString(i)) +" ";
                mapS += "0x"+memory.get(i).getHex()+ "\n";

            }
            return mapS;

        }

        System.out.println("YOUR RANGE IS ILLEGAL!");
        return "";

    }
    }

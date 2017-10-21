import java.io.*;


public class Translator {

    private  int start = 5;
    private  int end = 20;
    private Memory memMap = new Memory();


    public static void main(String[] args) throws IOException {
        Translator trans = new Translator();
        trans.readMemImage("S");
        Gui g = new Gui(trans.getMap());

    }

    public String getMap()
    {
        return memMap.stringMap(start,end);
    }



    void readMemImage(String inputFile) throws  IOException
    {

        BufferedReader reader =null;


        try {
            reader = new BufferedReader(new FileReader("/home/tokorealand/output.txt"));

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





    private void parseMem(String memLine) {
        String[] command = new String[6];
        String[] arrayLine;

        arrayLine=memLine.split("\\|");
        System.out.println(memLine);
        System.out.println(arrayLine[0]);


        if(arrayLine[0].contains("0x") && !arrayLine[0].contains("WS"))
        {
            memMap.addToMemory(arrayLine[1]);
            memMap.addToMemory(arrayLine[2]);
            memMap.addToMemory(arrayLine[3]);
            memMap.addToMemory(arrayLine[4]);
        }

    }








    String fromHexString(String hex, int bits) {
        int hexInt = Long.decode(hex).intValue();

        // System.out.println(hexInt);
        String holder = hexToBinary(hexInt);
        if(holder.length()<bits)
        {
            int padding = bits-holder.length();
            String pads="";
            for (int i=0; i<padding; i++)
            {
                pads+="0";
            }
            holder=pads+holder;

        }
        return holder;

    }

    public int fromBinaryToInt(String bin) {
        int binInt = Integer.parseInt(bin,2);

        return binInt;
    }

    private String hexToBinary(int hex) {
        String bin = Integer.toBinaryString(hex);
        return bin;
    }

    private String binarytoHex(String bin) {
        int decimal = Integer.parseInt(bin,2);
        String hex = Integer.toString(decimal,16);
        return hex;
    }



}

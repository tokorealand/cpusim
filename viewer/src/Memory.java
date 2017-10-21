import java.util.ArrayList;

public class Memory {

    private ArrayList<Byte> memory = new ArrayList<Byte>();
    private int currentIndex=0;





    public Memory()
    {

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


    public void addToMemory(String bin)
    {

        memory.add(new Byte(bin));


    }


    void printMap(int start, int end)
    {

        if(start>-1 && memory.size()<memory.size()+1) {

            for (int i = start; i < end; i++) {
                System.out.println(memory.get(i).getBinary());


            }
            return;
        }


        System.out.println("YOUR RANGE IS ILLEGAL!");
    }

    String stringMap(int start, int end) {
        String mapS = "";

        if (start > -1 && memory.size() < memory.size() + 1) {

            for (int i = start; i < end; i++)
                {
                    mapS += "0x"+Integer.toHexString(i)+" ";
                    mapS += memory.get(i).getBinary() + "\n";

                }
                return mapS;

            }

        System.out.println("YOUR RANGE IS ILLEGAL!");
        return "";

    }
    }

import java.io.IOException;

public class Main {
//filepath, filename, hex, start, end
    public static void main(String[] args) throws IOException {
        try {
            Translator t = new Translator(args[0],args[1],args[2],args[3],args[4]);

            t.readMemImage();

                Gui gu = new Gui(t.getMap());



            }


        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Wrong number of arguments");
        }



    }
}
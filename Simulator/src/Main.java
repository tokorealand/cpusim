

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
try {
    Translator t = new Translator(args[0],args[1],args[2],args[3]);

    t.readMemFile();

    Gui gu = new Gui(t);

}
catch (ArrayIndexOutOfBoundsException e)
{
    System.out.println("Wrong number of arguments");
}



    }
}


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            Translator t = new Translator(args[0],args[1]);

            t.readAssemblyFile();

        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Wrong number of arguments");
        }



    }
}

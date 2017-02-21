/**
 * Created by SandrosLaptop on 21/02/2017.
 */

package mainpackage;
        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException;

public class FileManage {

    private static final String FILENAME_SIMPLE = "data/simple.txt";

    public static void readFileByLine(String fileName){

        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(fileName));

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

    public static void readFileByChar(String fileName){

        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            // on reconstruit la ligne ?
            String reconstructLine = "";

            br = new BufferedReader(new FileReader(fileName));

            int r;
            while ((r = br.read()) != -1) {
                char ch = (char) r;
                if(ch != '\n'){

                    reconstructLine += ch;
                }
                else
                {
                    // Attention n'affiche pas la dernière ligne si il n'y a pas de \n (mais bon c'est juste un test)
                    System.out.println(reconstructLine);
                    reconstructLine = "";
                }

            }


        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

    public static void main(String[] args) {

        readFileByChar(FILENAME_SIMPLE);

        // test
        /*
        if (Analyzer.checkCds("CDS")){
            System.out.println("CDS hihi");
        }*/

    }

}
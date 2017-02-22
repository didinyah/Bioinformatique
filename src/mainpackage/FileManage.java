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



    public static void read(String fileName){

        // Logique du code
        // On lit un fichier on sait au début, --> on est dans l'entête (on crée un dijoncteur HEADER = 1)
        // (optionel) on vérife avec des mots clè qu'on est dans l'entête au cas ou
        // pendant la phase d'en tête (HEADER = 1) on extracte toutes les infos importants
            // TODO cds list (ANALYZER)
            // ... (à vérifier avec l'enonce) (ANALYZER)
        // On trouve ORIGIN implique (HEADER = 0) (CONTENT = 1)
        // pendant la phase de lecture (CONTENT=1) on check si on est dans un cds ou pas
        // une fois trouvé ! (et verifier)
            // TODO on calcul avec les phases 0 1 2  (ANALYZER)
            // on ressort 3 HMAP (ou on additione à un HMAP global)
            // TODO on calcul les pref  (ANALYZER)
            // Si on stock tout les HMAP on peut faire post processing
            // Sinon on ajoute dans un HMAP des pref +1 si la condition est verifié (voir énoncé)
        // on resort du cds
        // on continue et on refait la même chose si on retrouve un cds  sinon :
        // On sort de la lecture (CONTENT = 0) et on reva dans un en tête (HEADER = 1) (il faut trouver un connecteur de fin)
        // puis on recommence jusqu'à la fin de la lecture du fichier
        // résultat on aura 3 HMAP pour phase 0 1 2 et 3 HMAP de pref 0 1 2


        // DE MÊME AVEC LES DINUCLEOTIDE !!!!

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
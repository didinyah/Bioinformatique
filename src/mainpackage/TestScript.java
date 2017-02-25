package mainpackage;

/**
 * Ce fichier n'est pas un fichier de test unitaire ou autre c'est un fichier pour tester des scripts ou du code
 * Attention !! ce fichier sera supprimé
 *
 * Il permet pendant le dévelopement de comprendre comment fonctionne certain objet et code avec des exemples
 *
 * Il se présente de la forme suivante des fonctions static et un main qui choisit la fonction qui va lancé
 * (c'est brut si vous avez une autre façons simple je suis preneur)
 */
public class TestScript {

    public static void testNucleotide(){

        Trinucleotide tridata = new Trinucleotide();
        System.out.println(tridata.getHMAP().size());
        tridata.addTriN("aaa",1);
        System.out.println("Nombre de aaa :" + tridata.getHMAP().get("aaa"));

        System.out.println("Nombre de aac :" + tridata.getHMAP().get("aac"));
        Dinucleotide didata = new Dinucleotide();
        System.out.println(didata.getHMAP().size());

    }


    public static void testFile(){


        // file test

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));

        FileManage.readFileByChar(FileManage.FILENAME_SIMPLE);

        // test
        /*
        if (Analyzer.checkCds("CDS")){
            System.out.println("CDS hihi");
        }*/
    }
    public static void main(String[] args) {

        System.out.println("Hello World");
        //load data
        // test

        System.out.println("***************************************");

        testNucleotide();

    }
}

package mainpackage;

import java.util.ArrayList;
import java.util.List;

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

        GestionFichier.readFileByChar(GestionFichier.FILENAME_SIMPLE);

        // test
        /*
        if (Analyzer.checkCds("CDS")){
            System.out.println("CDS hihi");
        }*/
    }

    public static void testTrinucleotideOriginExtractor(){
        String str = "ORIGIN      \n" +
                "        1 actgcaggcg tcgagttcat tgaagagaaa ggtggtgggg ctggagttag gttgaggaaa\n" +
                "       61 tgaattgtgt aaaagtcgat cccaataaaa gtttcactgc ccctaccggc tttccggtat\n" +
                "      121 gcccatcacc cgcgcacgga gggcctcaaa ccgtgcaatc tgggcagggc ttggcttgcg\n" +
                "      181 ggggatgcat cttttcgatg tgttctgaaa acacttggct gattttattg gtctttttgg\n" +
                "      241 acgtgacccc gaaaaccgcg ccacgtcaga atctcaaaac atgggaaatc cccatgttat\n" +
                "      301 agattcccct gttatgggga tttactcccg gcatcccggt agtttctgaa actgtgcagt";

        // On suppose qu'on a un CDS de  65 à 260
        Analyzer anal = new Analyzer();
        anal.addListBorne(65,260);
        //TODO ORIGIN check ?? (à ignorer peut être car c'est pas forcèment le but du test)
        //TODO phase 0 1 2 et resort un HMAP :)
        // TODO Freq

    }

    public static void testCdsExtractor(){
        // Extrait d'un fichier d'exemple
        // les 5 premières lignes sont apriori bonne
        // la 6 eme est un test de fail car le symbole < est présent (PS si vous trouvez des CDS particuliers vous pouvez les insérer ici pour voir si sa work)
        // la dernière est pour tester un bug possible car dans la séquence suivante il y a le mot CDS
        List<String> listLineCds = new ArrayList<String>();
        listLineCds.add("     CDS             complement(388..813)");
        listLineCds.add("     CDS             complement(1206..2420)");
        listLineCds.add("     CDS             complement(2681..3160)");
        listLineCds.add("     CDS             3400..3927");
        listLineCds.add("     CDS             4064..5311");
        listLineCds.add("     CDS             <143859..144731");
        listLineCds.add("                     KLITNSGATIYPFKKSENIYSFMLPAGVESVRVVSRSSRPCDSIGPFVDDRRQMGVAV");


        // TODO fonction check + extract info par ligne
        // TODO créer une list de Borne de cds (Voir Analyzer Borne)
        // TODO test pour savoir si CheckIsInCds fonctionne



    }

    public static void main(String[] args) {

        System.out.println("Hello World");
        //load data
        // test

        System.out.println("***************************************");

        testNucleotide();

    }
}

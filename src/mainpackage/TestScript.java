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

        GestionFichier.read("files/tests/GCF_000010865.1_ASM1086v1_genomic.gbff");

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
        // le 7eme est pour tester un bug possible car dans la séquence suivante il y a le mot CDS
        // le 8eme est un fail car join plus petit que l'autre
        List<String> listLineCds = new ArrayList<String>();
        listLineCds.add("     CDS             complement(388..813)");
        listLineCds.add("     CDS             complement(1206..2420)");
        listLineCds.add("     CDS             complement(2681..3160)");
        listLineCds.add("     CDS             3400..3927");
        listLineCds.add("     CDS             4064..5311");
        listLineCds.add("     CDS             <143859..144731");
        listLineCds.add("                     KLITNSGATIYPFKKSENIYSFMLPAGVESVRVVSRSSRPCDSIGPFVDDRRQMGVAV");
        listLineCds.add("     CDS             complement(join(182789..182940,1..571))");
        listLineCds.add("     CDS             join(54151..54545,54597..54999,55107..>55883)");
        listLineCds.add("     CDS             complement(join(<9538..9972,10012..10266,10843..11391))\n");
        // TODO comment interpreter ces cas là sur plusieurs lignes? pendant le reader line car sa saute actuellement la deuxième partie
        // cas 2
        listLineCds.add("     CDS             join(1950..2004,2076..2861,2933..3211,3281..3291,\n" +
                "                     3343..3597,3670..4362)");// fuuu
        //cas 3
        listLineCds.add("     CDS             join(104713..104780,104838..105015,105068..105285,\n" +
                "                     105347..105480,105539..105774,105825..106013,\n" +
                "                     106073..106180,106317..106559,106628..106828)");
        //cas 4 (conclusion à généraliser)
        listLineCds.add("     CDS             join(172641..172693,172748..172841,172899..173051,\n" +
                "                     173101..173382,173442..173548,173598..173839,\n" +
                "                     173895..174048,174102..174369,174425..174639,\n" +
                "                     174697..174820,174906..175134,175196..175275)");
        // mix complement join 4 line l'enfer
        listLineCds.add("     CDS             complement(join(20208..20521,20614..20708,20772..20895,\n" +
                "                     20954..21169,21231..21240,21292..21353,21408..21492,\n" +
                "                     21544..21910,21956..21982,22036..22214,22265..22447,\n" +
                "                     22497..22551,22601..23079,23131..23183,23237..23912))");


        // TODO fonction check + extract info par ligne
        // TODO créer une list de Borne de cds (Voir Analyzer Borne)
        // TODO test pour savoir si CheckIsInCds fonctionne



    }

    public static void main(String[] args) {

        System.out.println("Hello World");
        //load data
        // test

        System.out.println("***************************************");

        testFile();


        try {
            Analyzer.join("join(50..60,80..90)");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

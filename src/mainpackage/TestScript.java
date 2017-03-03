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

        //GestionFichier.read("files/tests/GCF_000010865.1_ASM1086v1_genomic.gbff");
        GestionFichier.read("files/tests/GCA_001572075.1_ASM157207v1_genomic.gbff");
        // test
        /*
        if (Analyzer.checkCds("CDS")){
            System.out.println("CDS hihi");
        }*/
    }

    public static void testTrinucleotideOriginExtractor(){

        List<String> listLineContent = new ArrayList<String>();
        listLineContent.add("ORIGIN      " );
        listLineContent.add("        1 actgcaggcg tcgagttcat tgaagagaaa ggtggtgggg ctggagttag gttgaggaaa");
        listLineContent.add("       61 tgaattgtgt aaaagtcgat cccaataaaa gtttcactgc ccctaccggc tttccggtat");
        listLineContent.add("      121 gcccatcacc cgcgcacgga gggcctcaaa ccgtgcaatc tgggcagggc ttggcttgcg");
        listLineContent.add("      181 ggggatgcat cttttcgatg tgttctgaaa acacttggct gattttattg gtctttttgg");
        listLineContent.add("      241 acgtgacccc gaaaaccgcg ccacgtcaga atctcaaaac atgggaaatc cccatgttat");
        listLineContent.add("      301 agattcccct gttatgggga tttactcccg gcatcccggt agtttctgaa actgtgcagt");


        //TODO ORIGIN check ?? (à ignorer peut être car c'est pas forcèment le but du test)
        //TODO phase 0 1 2 et resort un HMAP :)
        // TODO Freq

    }

    public static void testCdsExtractor(){
        // Extrait d'un fichier d'exemple
        // success test
        List<String> listLineCds = new ArrayList<String>();
        listLineCds.add("     CDS             complement(388..813)");
        listLineCds.add("     CDS             complement(1206..2420)");
        listLineCds.add("     CDS             complement(2681..3160)");
        listLineCds.add("     CDS             3400..3927");
        listLineCds.add("     CDS             4064..5311");
        // cas 2
        listLineCds.add("     CDS             join(1950..2004,2076..2861,2933..3211,3281..3291," +
                "3343..3597,3670..4362)");// fuuu
        //cas 3
        listLineCds.add("     CDS             join(104713..104780,104838..105015,105068..105285," +
                "105347..105480,105539..105774,105825..106013," +
                "106073..106180,106317..106559,106628..106828)");
        //cas 4 (conclusion à généraliser)
        listLineCds.add("     CDS             join(172641..172693,172748..172841,172899..173051," +
                "173101..173382,173442..173548,173598..173839," +
                "173895..174048,174102..174369,174425..174639," +
                "174697..174820,174906..175134,175196..175275)");
        // mix complement join 4 line l'enfer
        listLineCds.add("     CDS             complement(join(20208..20521,20614..20708,20772..20895," +
                "20954..21169,21231..21240,21292..21353,21408..21492," +
                "21544..21910,21956..21982,22036..22214,22265..22447," +
                "22497..22551,22601..23079,23131..23183,23237..23912))");


        // crash test :
        listLineCds.add("     CDS             <143859..144731"); // line
        listLineCds.add("                     KLITNSGATIYPFKKSENIYSFMLPAGVESVRVVSRSSRPCDSIGPFVDDRRQMGVAV"); //line 8
        listLineCds.add("     CDS             complement(join(182789..182940,1..571))");
        listLineCds.add("     CDS             join(54151..54545,54597..54999,55107..>55883)");
        listLineCds.add("     CDS             complement(join(<9538..9972,10012..10266,10843..11391))");
        listLineCds.add("     CDS             complement(join(20208..20521,20614..20708,20772..20895," +
                "20954..21169,21231..21240,21292..21353,21408..21492," +
                "21544..21910,21956..21982,22036..5,22265..22447," + // un piège ici ->5<-
                "22497..22551,22601..23079,23131..23183,23237..23912))");
        listLineCds.add("     CDS             join(1950..2004,2000..2861)"); // chevauchement


        // test pas sur du résultat TODO à confirmer
        listLineCds.add("     CDS             join( 1950..2004,2000..2861)"); // crash car espace
        listLineCds.add("     CDS             4064 .. 5311"); // crash car espace
        listLineCds.add("     CDS             complement( 2681..3160 )");
        listLineCds.add("     CDS             join(complement(2681..3160))"); // crash car  inverser join complement 'test théorique"
        listLineCds.add("     CDS             join(2681..3160)"); // le dernier n'est pas considerer comme un bug pourtant un seul join sa parrait bizzare (TODO à verifier)
        listLineCds.add("     CDS             complement(join(2681..3160))"); // de même pour lui bizarre (il marche)

        int count_iscds = 0;
        int count_isnotcds =0;
        int crash_count = 0;
        int succes_count = 0;
        int i= 0;
        for(String cdsLine : listLineCds){
            i++;
            if(Analyzer.checkCds(cdsLine)){
                count_iscds++;
            }else{
                count_isnotcds++;
            }
            try {
                List<Analyzer.Borne> tt = Analyzer.cdsToBornes(cdsLine);
                succes_count++;

            } catch (Exception e) {
                crash_count++;

                System.out.println("line crash :"+i  + " \""+cdsLine+"\"");
            }
        }

        System.out.println("********BILAN***********");
        System.out.println("Count is cds : "+count_iscds);
        System.out.println("Count is not cds : "+count_isnotcds);
        System.out.println("Test is true cds : "+succes_count);
        System.out.println("\t is fake cds : "+crash_count);




    }

    public static void main(String[] args) {

        System.out.println("Hello World");
        //load data
        // test

        System.out.println("***************************************");

        //testCdsExtractor();
        testFile();
        testNucleotide();

    }
}

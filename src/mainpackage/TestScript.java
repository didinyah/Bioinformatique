package mainpackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
        System.out.println(tridata.getHMAP0().size());
        tridata.addTriN("aaa",1,1);
        System.out.println("Nombre de aaa :" + tridata.getHMAP0().get("aaa"));

        System.out.println("Nombre de aac :" + tridata.getHMAP0().get("aac"));
        Dinucleotide didata = new Dinucleotide();
        System.out.println(didata.getHMAP0().size());

    }


    public static ResultData testFile() throws IOException {


        // file test

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        //ResultData r = GestionFichier.read("files/tests/GCF_000847225.1_ViralMultiSegProj14603_genomic.gbff"); // PETIT
        //ResultData r = GestionFichier.read("files/tests/GCF_000010865.1_ASM1086v1_genomic.gbff"); // MOYEN
        //ResultData r = GestionFichier.read("files/tests/GCA_001572075.1_ASM157207v1_genomic.gbff"); // GROS
        ResultData r = GestionFichier.read("files/tests/GCF_000277815.2_ASM27781v3_genomic.gbff");
        //ResultData r = GestionFichier.read("files/tests/chromosome_VI_NC_028455.1.txt");
        //GestionFichier.read("files/tests/test.txt");
        // test
        /*
        if (Analyzer.checkCds("CDS")){
            System.out.println("CDS hihi");
        }*/

        return r;
    }

    public static void testTrinucleotideExtractor(){

        String lineContent =  "atggcaggcgtcgagttcattgaagagaaaggtggtggggctggagttagagttgaggaaa"+
                "tgaattgtgtaaaagtcgatcccaataaaatttcactgcaccctaccggcatttccggtat"+
                "gcccatcacccgcgcacggagggcctcaaaccgtgcaatctgggcagggcttggcttgcg"+
                "ggggatgcatcttttcgatgtgttctgaaacacttggctagattttattgatctttttgg"+
                "acgtgaccccgaaaaccgcgccacgtcagaatctcaaaacatgggaaatcaccatgttat"+
                "agattcccctgttatggggattactcccggcatcccggtaagtttctgaaactgttta";

        String line2 = "atgrrrtta";

        Trinucleotide ttt = new Trinucleotide();

        Trinucleotide tttCds = new Trinucleotide();
        ArrayList<String> rTri = Utils.getListOfTriNucleotide();
        String strAllTri = "";
        for(String tmp : rTri){
            strAllTri += tmp;
        }
        strAllTri = Utils.getListOfCodonInit().get(0) + strAllTri + Utils.getListOfCodonStop().get(0);

        System.out.println(strAllTri);
        try {
            Analyzer.countTrinIn3PhasesFromString(strAllTri,ttt,tttCds);

            Analyzer.countTrinIn3PhasesFromString(lineContent,ttt,tttCds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(ttt.getHMAP0());

        System.out.println(ttt.getHMAP1());

        System.out.println(ttt.getHMAP2());

        System.out.println(" [0:]    " +ttt.sumNumberOfNucleotide(0) + "  [1:]   "+ttt.sumNumberOfNucleotide(1) + "   [2:]    "+ttt.sumNumberOfNucleotide(2) );

    }


    public static void testTrinucleotideOriginExtractor(){

        List<String> listLineContent = new ArrayList<String>();
        listLineContent.add("        1 actgcaggcg tcgagttcat tgaagagaaa ggtggtgggg ctggagttag gttgaggaaa");
        listLineContent.add("       61 tgaattgtgt aaaagtcgat cccaataaaa gtttcactgc ccctaccggc tttccggtat");
        listLineContent.add("      121 gcccatcacc cgcgcacgga gggcctcaaa ccgtgcaatc tgggcagggc ttggcttgcg");
        listLineContent.add("      181 ggggatgcat cttttcgatg tgttctgaaa acacttggct gattttattg gtctttttgg");
        listLineContent.add("      241 acgtgacccc gaaaaccgcg ccacgtcaga atctcaaaac atgggaaatc cccatgttat");
        listLineContent.add("      301 agattcccct gttatgggga tttactcccg gcatc");



        for(String str : listLineContent){

            try {
                System.out.println(Analyzer.extractContentLine(str));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
                Bornes tt = Analyzer.cdsToBornes(cdsLine);
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

        System.out.println("********TEST2***********");
        List<String> test2 = new ArrayList<String>();
        test2.add("     CDS             join(<33903271..33903338,33903565..33903632,");
        test2.add("                     33904400..33904472,33904649..33904758,33904828..33904929,");
        test2.add("                     33905235..33905334,33905482..33905658)");
        try {
            String tmpcds = Analyzer.cdsMultiLineToString(test2);
            System.out.println(tmpcds);
            System.out.println(Analyzer.cdsToBornes(tmpcds));

        } catch (Exceptions.ExceptionCds ex)
        {
           System.out.println("error");
        }
        catch (Exceptions.ExceptionPatternLine ex)
        {
            System.out.println("error");

        }
        catch(Exceptions.ExceptionBorne exceptionCds) {

            System.out.println("error");
        }


    }

    public static void main(String[] args) {

        System.out.println("Hello World");
        //load data
        // test

        System.out.println("***************************************");

        //testCdsExtractor();
        try {
            ResultData r = testFile();
            System.out.println(r);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // testNucleotide();
        //testTrinucleotideExtractor();
        //testTrinucleotideOriginExtractor();
    }
}

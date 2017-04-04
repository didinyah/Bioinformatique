package mainpackage;

/**
 * Created by SandrosLaptop on 31/03/2017.
 */
public class ResultData {

    // Ce fichier consiste à rassembler toutes les informations de l'extractions d'un fichier ou de plusieurs fichiers

    // Todo la fusion d'un ResultData avec un autre
    // Todo la fusion d'une liste de ResultData
    // Ces prochaines fonctions permetrons pour générer les excels


    // Important var
    Trinucleotide ttt = null;
    Dinucleotide  dd = null;
    Integer numberCdsSeq = 0;
    Integer numberCdsSeqInvalid = 0;

    //Tri
    public Integer getTotalTri(int phase){
        return ttt.sumNumberOfNucleotide(phase);
    }

    public Double getTotalTriFreq(int phase){
        return ttt.sumFreqOfNucleotide(phase);
    }

    //Di
    public Integer getTotalDi(int phase){
        return dd.sumNumberOfNucleotide(phase);
    }
    public Double getTotalDiFreq(int phase){
        return dd.sumFreqOfNucleotide(phase);
    }


    public Trinucleotide getTrinucleotide() {
        return ttt;
    }

    public Dinucleotide getDinucleotide() {
        return dd;
    }

    public Integer getNumberCdsSeq() {
        return numberCdsSeq;
    }

    public Integer getNumberCdsSeqInvalid() {
        return numberCdsSeqInvalid;
    }




    // Optional var

    Integer lineCount = 0;
    Integer headerLine = 0;
    Integer contentLine = 0;

    // cds info
    // Donne le nombre de borne étant en mode complement (ce n'est pas le nombre de cds en complement)
    Integer cdsBorneComplement = 0;
    //Nombre de CDS qui sont sur plusieurs ligne (peu d'information à part que c'est une grosse jointure)
    Integer cdsMultiLine = 0;

    Integer blockTransition; // TODO voir si cela correspond à autre choes

    Integer borneComplementFail = 0;
    Integer inBorneContentFail = 0;
    Integer codonOfBornFail = 0;


    public String toString(){
        String str = "";
        str += "Line count:"+lineCount+"\n";

        str += "BlockTransition detected:" + blockTransition+"\n";
        str += "Header line:" + headerLine+"\n";
        str += "Content line:" + contentLine+"\n";
        str += "cds match:" + numberCdsSeq+"\n";
        str += "\tcds match fail :"+numberCdsSeqInvalid+"\n";

        str += "\tcds borne complement count :"+cdsBorneComplement+"\n";
        str += "\tcds multi line count :"+cdsMultiLine+"\n";
        str += "\tcds complement fail :"+borneComplementFail+"\n";
        str += "Content (pattern or letter bug) cds fail :"+inBorneContentFail+"\n";
        str += "Codon init or end from cds fail :"+codonOfBornFail+"\n";

        str += "Trinucleotide"+"\n";
        str += ttt.getHMAP0()+"\n";
        str += ttt.getHMAP1()+"\n";
        str += ttt.getHMAP2()+"\n";
        str += "Pref :"+"\n";
        str += ttt.getPrefHMAP0()+"\n";
        str += ttt.getPrefHMAP1()+"\n";
        str += ttt.getPrefHMAP2()+"\n";

        // Calcul des frequences :

        str += "Freq:"+"\n";
        str += ttt.getFreqHMAP0()+"\n";
        str += ttt.getFreqHMAP1()+"\n";
        str += ttt.getFreqHMAP2()+"\n";

        str += "Dinucleotide"+"\n";
        str += dd.getHMAP0()+"\n";
        str += dd.getHMAP1()+"\n";
        str += "Pref :"+"\n";
        str += dd.getPrefHMAP0()+"\n";
        str += dd.getPrefHMAP1()+"\n";

        // Calcul des frequences :

        str += "Freq:"+"\n";
        str += dd.getFreqHMAP0()+"\n";
        str += dd.getFreqHMAP1()+"\n";


        str += " Sum : "+"\n";
        str += " [0:]    " +getTotalTri(0) + "  [1:]   "+getTotalTri(1) + "   [2:]    "+getTotalTri(2) +"\n";
        str += " [0:]    " +getTotalDi(0) + "  [1:]   "+getTotalDi(1)  +"\n";

        str += " [0:]    " +getTotalTriFreq(0) + "  [1:]   "+getTotalTriFreq(1) + "   [2:]    "+getTotalTriFreq(2) +"\n";
        str += " [0:]    " +getTotalDiFreq(0) + "  [1:]   "+getTotalDiFreq(1)  +"\n";

        return str;
    }

}

package mainpackage;

import java.util.List;

/**
 * Created by SandrosLaptop on 31/03/2017.
 */
public class ResultData {

    // Ce fichier consiste à rassembler toutes les informations de l'extractions d'un fichier ou de plusieurs fichiers

	// Nom du resultdata (syntaxe : Sum_type || plasmid_NC_99999)
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

    // Important var
    Trinucleotide ttt = null;
    Dinucleotide  dd = null;
    Integer numberCdsSeq = 0;
    Integer numberCdsSeqInvalid = 0; // Nombre de cds mal formé

    Integer inBorneContentFail = 0; // Nombre de cds Invalide (pendant la phase de content)

    private boolean plasmid = false;
    private boolean chromosome =  false;
    private boolean linkage = false;
    private boolean chloroplast = false;
    private boolean mitochondrion = false;
    private boolean dna = false;

    private Integer nbOrganism;
    private Integer nbChromosome;
    private Integer nbPlasmid;
    private Integer nbDna;
    private String lastModifDate;
    
    public String getOrganismName() {
		return organismName;
	}

	public void setOrganismName(String organismName) {
		this.organismName = organismName;
	}

	private String organismName;
    
    
    public Integer getAllCDSSequence() {
		return allCDSSequence;
	}

	public void setAllCDSSequence(Integer allCDSSequence) {
		this.allCDSSequence = allCDSSequence;
	}

	public Integer getAllInvalidCDS() {
		return allInvalidCDS;
	}

	public void setAllInvalidCDS(Integer allInvalidCDS) {
		this.allInvalidCDS = allInvalidCDS;
	}

	public Integer getAllNbOrganism() {
		return allNbOrganism;
	}

	public void setAllNbOrganism(Integer allNbOrganism) {
		this.allNbOrganism = allNbOrganism;
	}

	public Integer getAllChromosome() {
		return allChromosome;
	}

	public void setAllChromosome(Integer allChromosome) {
		this.allChromosome = allChromosome;
	}

	public Integer getAllPlasmid() {
		return allPlasmid;
	}

	public void setAllPlasmid(Integer allPlasmid) {
		this.allPlasmid = allPlasmid;
	}

	public Integer getAllDNA() {
		return allDNA;
	}

	public void setAllDNA(Integer allDNA) {
		this.allDNA = allDNA;
	}

	private Integer allCDSSequence;
    private Integer allInvalidCDS;
    private Integer allNbOrganism;
    private Integer allChromosome;
    private Integer allPlasmid;
    private Integer allDNA;

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
    
    public void setNumberCdsSeq(Integer numberCdsSeq) {
        this.numberCdsSeq = numberCdsSeq;
    }
    
    public void setNumberCdsSeqInvalid(Integer numberCdsSeqInvalid) {
        this.numberCdsSeqInvalid = numberCdsSeqInvalid;
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

    Integer blockTransition= 0;

    Integer borneComplementFail = 0;
    Integer codonOfBornFail = 0;

    public ResultData(){
        ttt = new Trinucleotide();
        ttt.initPref();
        ttt.initFreq();
        dd = new Dinucleotide();
        dd.initPref();
        dd.initFreq();
    }
    
    // Fonctions d'accesseurs pour le type de la cellule

    public void setPlasmid(boolean plasmid) {
        this.plasmid = plasmid;
    }

    public void setChromosome(boolean chromosome) {
        this.chromosome = chromosome;
    }

    public void setLinkage(boolean linkage) {
        this.linkage = linkage;
    }

    public void setChloroplast(boolean chloroplast) {
        this.chloroplast = chloroplast;
    }

    public void setMitochondrion(boolean mitochondrion) {
        this.mitochondrion = mitochondrion;
    }
    
    public void setDna(boolean dna) {
    	this.dna = dna;
    }
    
    public  boolean isPlasmid() {
        return plasmid;
    }

    public  boolean isChromosome() {
        return chromosome;
    }

    public  boolean isLinkage() {
        return linkage;
    }

    public  boolean isChloroplast() {
        return chloroplast;
    }

    public  boolean isMitochondrion() {
        return mitochondrion;
    }
    
    public boolean isDna() {
    	return dna;
    }
    
    // Infos suppl�mentaires pour information g�n�rale
    
    public Integer getNbOrganism() {
    	return nbOrganism;
    }
    
    public void setNbOrganism(Integer nbOrganism) {
    	this.nbOrganism = nbOrganism;
    }
    
    public Integer getNbChromosome() {
    	return nbChromosome;
    }
    
    public void setNbChromosome(Integer nbChromosome) {
    	this.nbChromosome = nbChromosome;
    }
    
    public Integer getNbPlasmid() {
    	return nbPlasmid;
    }
    
    public void setNbPlasmid(Integer nbPlasmid) {
    	this.nbPlasmid = nbPlasmid;
    }
    
    public Integer getNbDna() {
    	return nbDna;
    }
    
    public void setNbDna(Integer nbDna) {
    	this.nbDna = nbDna;
    }
    
    public String getLastModifDate() {
    	return lastModifDate;
    }
    
    public void setLastModifDate(String lastModifDate) {
    	this.lastModifDate = lastModifDate;
    }

    public int getCDSRestants()
    {
    	return numberCdsSeq - numberCdsSeqInvalid;
    }
    
    public int getCDSTraites()
    {
    	return numberCdsSeq - numberCdsSeqInvalid - inBorneContentFail;
    }
    
    public int getCDSInvalide()
    {
    	return inBorneContentFail;
    }
    // fusion function
    public void fusion(ResultData resultData){
        // fusion current resultData with the resultdata in param
        ttt.fusion(resultData.ttt);
        dd.fusion(resultData.dd);

        ttt.fusionPref(resultData.ttt);
        dd.fusionPref(resultData.dd);
        try {
            ttt.calculFreq();
            dd.calculFreq();
        } catch (Exceptions.ExceptionCodonNotFound exceptionCodonNotFound) {

        }
        numberCdsSeq += resultData.numberCdsSeq;
        numberCdsSeqInvalid += resultData.numberCdsSeqInvalid;

        //optional
        lineCount += resultData.lineCount;
        headerLine += resultData.headerLine;
        contentLine += resultData.contentLine;
        cdsBorneComplement += resultData.cdsBorneComplement;
        cdsMultiLine += resultData.cdsMultiLine;
        blockTransition += resultData.blockTransition;
        inBorneContentFail += resultData.inBorneContentFail;
        codonOfBornFail += resultData.codonOfBornFail;
    }


    public void fusions(List<ResultData> listResult){
        for(ResultData r: listResult){
            fusion(r);
        }
    }

    public String toString(){
        String str = "";
        str += "Name:"+name+"\n";
        str += "Organism Name:"+organismName+"\n";
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

        str += " Sum freq : "+"\n";
        str += " [0:]    " +getTotalTriFreq(0) + "  [1:]   "+getTotalTriFreq(1) + "   [2:]    "+getTotalTriFreq(2) +"\n";
        str += " [0:]    " +getTotalDiFreq(0) + "  [1:]   "+getTotalDiFreq(1)  +"\n";

        str += " Sum Pref : "+"\n";
        str += " [0:]    " +ttt.sumPrefOfNucleotide(0) + "  [1:]   "+ttt.sumPrefOfNucleotide(1) + "   [2:]    "+ttt.sumPrefOfNucleotide(2) +"\n";
        str += " [0:]    " +dd.sumPrefOfNucleotide(0) + "  [1:]   "+dd.sumPrefOfNucleotide(1)  +"\n";


        return str;
    }

}

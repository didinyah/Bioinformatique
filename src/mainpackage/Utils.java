package mainpackage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* Classe Utils
 * 
 * Contient les fonctions et les variables utilitaires du projet,
 * celles utilisées par tout le monde.
 */
public class Utils {
	//Liste contenant toutes les combinaisons de nucléotides
	private static ArrayList<String> LSTTRINU = new ArrayList<String>(Arrays.asList(
			"aaa", "caa", "gaa", "taa",
			"aca", "cca", "gca", "tca",
			"aga", "cga", "gga", "tga",
			"ata", "cta", "gta", "tta",
			
			"acc", "ccc", "gcc", "tcc",
			"aac", "cac", "gac", "tac",
			"aag", "cag", "gag", "tag",
			"aat", "cat", "gat", "tat",
			
			"agg", "cgg", "ggg", "tgg",
			"agc", "cgc", "ggc", "tgc",
			"agt", "cgt", "ggt", "tgt",
			"acg", "ccg", "gcg", "tcg",
			
			"att", "ctt", "gtt", "ttt",
			"atg", "ctg", "gtg", "ttg",
			"atc", "ctc", "gtc", "ttc",
			"act", "cct", "gct", "tct"));
	
	private static ArrayList<String> LSTCINIT = new ArrayList<String>(Arrays.asList(
			"atg", "ctg", "ttg", "gtg", "ata", "atc", "att", "tta"));
	
	private static ArrayList<String> LSTCSTOP = new ArrayList<String>(Arrays.asList(
			"taa", "tag", "tga", "tta"));			
	
	private static ArrayList<String> LSTDINU = new ArrayList<String>(Arrays.asList(
			"aa", "ac", "at", "ag",
			"ca", "cc", "ct", "cg",
			"ta", "tc", "tt", "tg",
			"ga", "gc", "gt", "gg"));
	
	
	
	// Normalement les trinucléotide sont en minuscule j'ai quand même laissé les list en maj
	// à voir si c'est toujour d'actualité 
	private static ArrayList<String> LSTNUCL = new ArrayList<String>(Arrays.asList(
			"AAA", "CAA", "GAA", "TAA",
			"ACA", "CCA", "GCA", "TCA",
			"AGA", "CGA", "GGA", "TGA",
			"ATA", "CTA", "GTA", "TTA",
			
			"ACC", "CCC", "GCC", "TCC",
			"AAC", "CAC", "GAC", "TAC",
			"AAG", "CAG", "GAG", "TAG",
			"AAT", "CAT", "GAT", "TAT",
			
			"AGG", "CGG", "GGG", "TGG",
			"AGC", "CGC", "GGC", "TGC",
			"AGT", "CGT", "GGT", "TGT",
			"ACG", "CCG", "GCG", "TCG",
			
			"ATT", "CTT", "GTT", "TTT",
			"ATG", "CTG", "GTG", "TTG",
			"ATC", "CTC", "GTC", "TTC",
			"ACT", "CCT", "GCT", "TCT"));
	
	private static ArrayList<String> LSDNUCL = new ArrayList<String>(Arrays.asList(
			"AA", "AC", "AT", "AG",
			"CA", "CC", "CT", "CG",
			"TA", "TC", "TT", "TG",
			"GA", "GC", "GT", "GG"));
	// Pour l'excel, les titres des feuilles General_information
	private static ArrayList<String> EXCELTITLESGENERALINFO = new ArrayList<String>(Arrays.asList(
			"Information", "Name", "Modification Date", "Number of CDS sequences", 
			"Number of invalids CDS", "Number of Organisms"));
	
	private static ArrayList<String> EXCELTITLESGENOMEINFO = new ArrayList<String>(Arrays.asList(
			"Genome", "Chromosome", "Plasmid", "DNA"));
		
	// Pour l'excel, les titres des feuilles Sum_toto avec les phases
	private static ArrayList<String> EXCELTITLESPHASETRI = new ArrayList<String>(Arrays.asList(
			"Phase 0", "Freq Phase 0", "Phase 1", "Freq Phase 1", "Phase 2", "Freq Phase 2",
			"Pref Phase 0", "Pref Phase 1", "Pref Phase 2"));
	
	private static ArrayList<String> EXCELTITLESPHASEDI = new ArrayList<String>(Arrays.asList(
			"Phase 0", "Freq Phase 0", "Phase 1", "Freq Phase 1",
			"Pref Phase 0", "Pref Phase 1"));
	
	private static ArrayList<String> EXCELTITLESPHASEINFO = new ArrayList<String>(Arrays.asList(
			"Informations", "Number of CDS sequences", "Number of invalid CDS"));
	
	// [WEB] URL pour r�cup�rer les donn�es et cr�er l'arbre

	public static String TREE_EUKARYOTES_URL = "https://www.ncbi.nlm.nih.gov/genomes/Genome2BE/genome2srv.cgi?action=GetGenomes4Grid&king=Eukaryota&mode=2&filterText=%7C%7C--+All+Eukaryota+--%7C--+All+Eukaryota+--%7C%7C50%2C40%2C30%2C20%7Cnopartial%7Cnoanomalous&pageSize=100&page=";
	public static String TREE_PROKARYOTES_URL = "https://www.ncbi.nlm.nih.gov/genomes/Genome2BE/genome2srv.cgi?action=GetGenomes4Grid&king=Bacteria&mode=2&filterText=%7C%7C--+All+Prokaryotes+--%7C--+All+Prokaryotes+--%7C%7C50%2C40%7Cnopartial%7Cnoanomalous&pageSize=100&page=";
	public static String TREE_VIRUSES_URL = "https://www.ncbi.nlm.nih.gov/genomes/Genome2BE/genome2srv.cgi?action=GetGenomes4Grid&king=Viruses&mode=2&pageSize=100&page=";
	
	// [WEB] URL pour t�l�charger les donn�es
	
	public static String DOWNLOAD_NC_URL = "https://www.ncbi.nlm.nih.gov/sviewer/viewer.cgi?tool=portal&save=file&log$=seqview&db=nuccore&report=gbwithparts&sort=&from=begin&to=end&maxplex=3&id=<ID>";
	
	// Accesseurs
	public static ArrayList<String> getListOfTriNucleotide(){
		return LSTTRINU;
	}
	public static ArrayList<String> getListOfDiNucleotide(){
		return LSTDINU;
	}
	
	public static ArrayList<String> getListOfCodonInit(){
		return LSTCINIT;
	}
	
	public static ArrayList<String> getListOfCodonStop(){
		return LSTCSTOP;
	}
 
	public static ArrayList<String> getListOfTriNucleotideCAPSLOCK(){
		return LSTNUCL;
	}
	
	public static ArrayList<String> getListOfDiNucleotideCAPSLOCK(){
		return LSDNUCL;
	}
	
	public static ArrayList<String> getExcelTitlesGeneralInfo(){
		return EXCELTITLESGENERALINFO;
	}
	public static ArrayList<String> getExcelTitlesGenomeInfo(){
		return EXCELTITLESGENOMEINFO;
	}
	
	public static ArrayList<String> getExcelTitlesPhaseTri(){
		return EXCELTITLESPHASETRI;
	}
	
	public static ArrayList<String> getExcelTitlesPhaseDi(){
		return EXCELTITLESPHASEDI;
	}
	
	public static ArrayList<String> getExcelTitlesPhaseInfo(){
		return EXCELTITLESPHASEINFO;
	}
	
	// Zipper tous les fichiers d'un dossier
	public static void ZipFiles(String path) {
		try {
			 final int BUFFER = 2048;
	         BufferedInputStream origin = null;
	         FileOutputStream dest = new FileOutputStream(path + ".zip");
	         ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
	         //out.setMethod(ZipOutputStream.DEFLATED);
	         byte data[] = new byte[BUFFER];
	         // get a list of files from current directory
	         File f = new File(path + Configuration.DIR_SEPARATOR);
	         //System.out.println("chemin : " + f.getPath());
	         //System.out.println("dossier ? : " + f.isDirectory());
	         String files[] = f.list();

	         for (int i=0; i<files.length; i++) {
	            System.out.println("Adding: "+files[i]);
	            FileInputStream fi = new FileInputStream(path + Configuration.DIR_SEPARATOR +files[i]);
	            origin = new BufferedInputStream(fi, BUFFER);
	            ZipEntry entry = new ZipEntry(files[i]);
	            out.putNextEntry(entry);
	            int count;
	            while((count = origin.read(data, 0, BUFFER)) != -1) {
	               out.write(data, 0, count);
	            }
	            origin.close();
	         }
	         out.close();
	    } 
		catch(Exception e) {
			e.printStackTrace();
	    }
	}
	
	// Fonctions utiles pour ResultData
	
	// On fait un resultdata contenant les infos suivantes : nb d'organismes, de chromosomes, de dna, de plasmids et la derni�re date de modification de l'organisme
	public static ResultData setGeneralInformationRD(int nbOrganism, int nbChromosome, int nbPlasmid, int nbDna, String lastModifDate, String name, int numberCDS, int numberCDSInvalid) {
		ResultData rdGeneralInformation = new ResultData();
		rdGeneralInformation.setName("General_Information");
		rdGeneralInformation.setNbOrganism(nbOrganism);
		rdGeneralInformation.setNbChromosome(nbChromosome);
		rdGeneralInformation.setNbPlasmid(nbPlasmid);
		rdGeneralInformation.setNbDna(nbDna);
		rdGeneralInformation.setLastModifDate(lastModifDate);
		rdGeneralInformation.setOrganismName(name);
		rdGeneralInformation.setNumberCdsSeq(numberCDS);
		rdGeneralInformation.setNumberCdsSeqInvalid(numberCDSInvalid);
		return rdGeneralInformation;
	}
}

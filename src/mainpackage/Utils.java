package mainpackage;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	//Accesseurs
	public static ArrayList<String> getListOfTriNucleotide(){
		return LSTTRINU;
	}
	public static ArrayList<String> getListOfDiNucleotide(){
		return LSTDINU;
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
}

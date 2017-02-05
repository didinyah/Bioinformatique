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
	private static ArrayList<String> LSTTRINU = new ArrayList<>(Arrays.asList(
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
	
	
	private static ArrayList<String> LSTDINU = new ArrayList<>(Arrays.asList(
			"aa", "ac", "at", "ag",
			"ca", "cc", "ct", "cg",
			"ta", "tc", "tt", "tg",
			"ga", "gc", "gt", "gg"));
	
	
	
	// Normalement les trinucléotide sont en minuscule j'ai quand même laissé les list en maj
	// à voir si c'est toujour d'actualité 
	private static ArrayList<String> LSTNUCL = new ArrayList<>(Arrays.asList(
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
	
	private static ArrayList<String> LSDNUCL = new ArrayList<>(Arrays.asList(
			"AA", "AC", "AT", "AG",
			"CA", "CC", "CT", "CG",
			"TA", "TC", "TT", "TG",
			"GA", "GC", "GT", "GG"));
	
	//Accesseurs
	
	public static ArrayList<String> getLSTTRINU(){
		return LSTTRINU;
	}
	
	public static ArrayList<String> getLSTDINU(){
		return LSTDINU;
	}
	
	//Beautifull accesseurs ?? (utile ?)
	public static ArrayList<String> getListOfTriNucleotide(){
		return Utils.getLSTTRINU();
	}
	public static ArrayList<String> getListOfDiNucleotide(){
		return Utils.getLSTDINU();
	}
	
	//Accesseurs old 
	public static ArrayList<String> getLSTNUCL(){
		return LSTNUCL;
	}
	
	public static ArrayList<String> getLSDNUCL(){
		return LSDNUCL;
	}
}

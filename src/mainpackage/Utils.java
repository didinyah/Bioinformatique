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
	
	//Accesseurs
	public static ArrayList<String> getLSTNUCL(){
		return LSTNUCL;
	}
}

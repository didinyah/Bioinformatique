package mainpackage;

/* 
 * Classe Convention
 * Contient la nomenclature du projet
 * 
 * C'est à dire : comment nommer les classes, les fonctions et les variables
 * ainsi que comment commenter.
 */
public class Convention {
	//Variables
	//Constante de test
	private static int CONST = 0;
	//Variable de test
	private String nucleotideOne = "AAA";
	
	//Accesseurs
	static int GetCONST(){
		return CONST;
	}
	
	String GetNucleotide(){
		return this.nucleotideOne;
	}
	
	void SetNucleotide(String n){
		this.nucleotideOne = n;
	}
	//Méthodes
	/* 
	 * checkNucleotide
	 * String n : le nucléotide
	 * Description : teste si un nucléotide est valide
	 * Return : true si le nucléotide est valide, false sinon
	 */
	boolean checkNucleotide(String n){
		return true;
	}
}

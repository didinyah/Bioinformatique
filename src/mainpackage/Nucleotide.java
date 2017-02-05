package mainpackage;

import java.util.HashMap;

public class Nucleotide {
	// La classe mère
	// (à voir si c'est utile) 
	
	// On définit les constants 
	private HashMap<String, Integer> HMAP = new HashMap<String, Integer>();

	
	//acc
	public HashMap<String, Integer> GetHMAP(){
		return this.HMAP;
	}
	
	public void SetHMAP(HashMap<String, Integer> hm){
		this.HMAP = hm;
	}
	// Todo fonction de frequence 
	
	
	
}






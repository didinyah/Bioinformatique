package mainpackage;

import java.util.HashMap;



public class Trinucleotide extends Nucleotide{	
	// Un tableau de trinucléotide
	// (On le définit en dur car c'est plus optimal que de les générer) 
	
	
	public Trinucleotide(){
		
		HashMap<String, Integer> HMAP = this.getHMAP();
		
		
		for (String temp : Utils.getListOfTriNucleotide()) {
			HMAP.put(temp, 0);
		}
		
	}
	
	

	public void addTriN(String triN,int i){
		HashMap<String, Integer> HMAP = this.getHMAP();
		HMAP.put(triN,1+HMAP.get(triN));
		
	}
	
	// Remove ? 
	
	
	// Todo Fusion de deux tableaux 
	
	//public void addHashMap() 
	
	// Todo fusion avec un autre trinucl
	
	
}
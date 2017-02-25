package mainpackage;

import java.util.HashMap;

public class Dinucleotide extends Nucleotide{	
	// Un tableau de binucléotide 
	// (De même) 
	

	public Dinucleotide(){
		
		HashMap<String, Integer> HMAP = this.getHMAP();
		
		
		for (String temp : Utils.getListOfDiNucleotide()) {
			HMAP.put(temp, 0);
		}
		
	}
	
}

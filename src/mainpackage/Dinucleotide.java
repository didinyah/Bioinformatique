package mainpackage;

import java.util.HashMap;

public class Dinucleotide extends Nucleotide{	
	// Un tableau de binucléotide 
	// (De même) 
	

	public Dinucleotide(){
		
		HashMap<String, Integer> HMAP0 = this.getHMAP0();
		HashMap<String, Integer> HMAP1 = this.getHMAP1();
		HashMap<String, Integer> HMAP2 = this.getHMAP2();
		
		for (String temp : Utils.getListOfDiNucleotide()) {
			HMAP0.put(temp, 0);
			HMAP1.put(temp, 0);
			HMAP2.put(temp, 0);
		}
		
	}
	
}

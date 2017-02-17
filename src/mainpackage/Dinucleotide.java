package mainpackage;

import java.util.HashMap;

import mainpackage.Nucleotide;
import mainpackage.Utils;

public class Dinucleotide extends Nucleotide{	
	// Un tableau de binucléotide 
	// (De même) 
	

	public Dinucleotide(){
		
		HashMap<String, Integer> HMAP = this.GetHMAP();
		
		
		for (String temp : Utils.getListOfDiNucleotide()) {
			HMAP.put(temp, 0);
		}
		
	}
	
}

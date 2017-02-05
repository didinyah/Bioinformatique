package mainpackage;

import mainpackage.Nucleotide;
import mainpackage.Utils;

import java.util.HashMap;



public class Trinucleotide extends Nucleotide{	
	// Un tableau de trinucléotide
	// (On le définit en dur car c'est plus optimal que de les générer) 
	
	
	public Trinucleotide(){
		
		HashMap<String, Integer> HMAP = this.GetHMAP();
		
		
		for (String temp : Utils.getLSTTRINU()) {
			HMAP.put(temp, 0);
		}
		
	}
	
	
	
	// Todo faire une fonction qui incrémente un trinucleotide 
	public void addTriN(String triN,int i){
		
	}
	
	// Remove ? 
	
	
	// Todo Fusion de deux tableaux 
	
	//public void addHashMap() 
	
	// Todo fusion avec un autre trinucl
	
	
}
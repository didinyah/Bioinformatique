package mainpackage;

import java.util.HashMap;
import java.util.Map;


public class Trinucleotide extends Nucleotide{
	// Un tableau de trinucléotide
	// (On le définit en dur car c'est plus optimal que de les générer) 
	
	
	public Trinucleotide(){
		
		HashMap<String, Integer> HMAP0 = this.getHMAP0();
		HashMap<String, Integer> HMAP1 = this.getHMAP1();
		HashMap<String, Integer> HMAP2 = this.getHMAP2();
		
		
		for (String temp : Utils.getListOfTriNucleotide()) {
			HMAP0.put(temp, 0);
			HMAP1.put(temp, 0);
			HMAP2.put(temp, 0);
		}
		
	}
	
	

	public void addTriN(String triN,int i, int phase){
		HashMap<String, Integer> HMAP = null;
		switch(phase){
		case 0:
			HMAP = getHMAP0();
			break;
		case 1:
			HMAP = getHMAP1();
			break;
		case 2:
			HMAP = getHMAP2();
			break;
		default:
			HMAP = getHMAP0();
		}
		HMAP.put(triN,i+HMAP.get(triN));	
	}
	
	// Remove ? 
	
	
	// Todo Fusion de deux tableaux 
	
	//public void addHashMap() 
	
	// Todo fusion avec un autre trinucl


	public int countNumberOfTrinucleotide(int phase){
		HashMap<String, Integer> HMAP = null;
		switch(phase){
			case 0:
				HMAP = getHMAP0();
				break;
			case 1:
				HMAP = getHMAP1();
				break;
			case 2:
				HMAP = getHMAP2();
				break;
			default:
				HMAP = getHMAP0();
		}
		int tmp = 0;
		for(Map.Entry<String, Integer> entry : HMAP.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			tmp += value;
		}

		return tmp;
	}
	
	
}
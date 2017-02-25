package mainpackage;

import java.util.HashMap;

public class Nucleotide {
	// La classe mère
	// (à voir si c'est utile) 
	
	// On définit les constants 
	private HashMap<String, Integer> HMAP = new HashMap<String, Integer>();

	
	//acc
	public HashMap<String, Integer> getHMAP(){
		return this.HMAP;
	}
	
	public void setHMAP(HashMap<String, Integer> hm){
		this.HMAP = hm;
	}

	/* Fait la fusion de HMAP càd additionne les deux hmap en envoie les résultats dans le hmap mère*/
	// Todo à tester
	public void fusion(HashMap<String, Integer> hm){
		HashMap<String, Integer> HMAP = getHMAP();
		for(HashMap.Entry<String, Integer> entry : hm.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			HMAP.put(key,HMAP.get(key) + value);
		}

	}

	// Todo fonction de frequence
	// à reflechir de les mettres

	
	
	
}






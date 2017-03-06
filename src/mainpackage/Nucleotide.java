package mainpackage;

import java.util.HashMap;

public class Nucleotide {
	// La classe mère
	// (à voir si c'est utile) 
	
	// On définit les constants 
	private HashMap<String, Integer> HMAP0 = new HashMap<String, Integer>();
	private HashMap<String, Integer> HMAP1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> HMAP2 = new HashMap<String, Integer>();

	
	//acc
	public HashMap<String, Integer> getHMAP0(){
		return this.HMAP0;
	}
	
	public void setHMAP(HashMap<String, Integer> hm){
		this.HMAP0 = hm;
	}

	public HashMap<String, Integer> getHMAP1() {
		return HMAP1;
	}

	public void setHMAP1(HashMap<String, Integer> hMAP1) {
		HMAP1 = hMAP1;
	}

	public HashMap<String, Integer> getHMAP2() {
		return HMAP2;
	}

	public void setHMAP2(HashMap<String, Integer> hMAP2) {
		HMAP2 = hMAP2;
	}


	/* Fait la fusion de HMAP càd additionne les deux hmap en envoie les résultats dans le hmap mère*/

	public void fusion(HashMap<String, Integer> hm, int phase){
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

		for(HashMap.Entry<String, Integer> entry : hm.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			HMAP.put(key,HMAP.get(key) + value);
		}

	}

	public void fusion(Nucleotide tmp,int phase){
		switch(phase){
			case 0:
				fusion(tmp.getHMAP0(),0);
				break;
			case 1:
				fusion(tmp.getHMAP1(),1);
				break;
			case 2:
				fusion(tmp.getHMAP2(),2);
				break;
			default:
				fusion(tmp.getHMAP0(),3);
		}
	}

	public void fusion(Nucleotide tmp){

		fusion(tmp.getHMAP0(),0);
		fusion(tmp.getHMAP1(),1);
		fusion(tmp.getHMAP2(),2);
	}
	// Todo fonction de frequence
	// à reflechir de les mettres


	public String toString(){

		return "Trinucleotide :  HMAP0\n"+ HMAP0.toString();
	}
	
	
}






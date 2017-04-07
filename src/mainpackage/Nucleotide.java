package mainpackage;

import java.util.HashMap;
import java.util.Map;

public class Nucleotide {
	// La classe mère
	// (à voir si c'est utile) 
	
	// On définit les constants 
	private HashMap<String, Integer> HMAP0 = new HashMap<String, Integer>();
	private HashMap<String, Integer> HMAP1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> HMAP2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> prefHMAP0 = null;
	private HashMap<String, Integer> prefHMAP1 = null;
	private HashMap<String, Integer> prefHMAP2 = null;
	private HashMap<String, Double> freqHMAP0 = null;
	private HashMap<String, Double> freqHMAP1 = null;
	private HashMap<String, Double> freqHMAP2 = null;
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

	public HashMap<String, Integer> getPrefHMAP0() {
		return prefHMAP0;
	}

	public HashMap<String, Integer> getPrefHMAP1() {
		return prefHMAP1;
	}

	public HashMap<String, Integer> getPrefHMAP2() {
		return prefHMAP2;
	}

	public HashMap<String, Double> getFreqHMAP0() {
		return freqHMAP0;
	}

	public HashMap<String, Double> getFreqHMAP1() {
		return freqHMAP1;
	}

	public HashMap<String, Double> getFreqHMAP2() {
		return freqHMAP2;
	}

	public HashMap<String, Integer> getHMAP(int phase) {
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
		return HMAP;
	}

	public HashMap<String, Integer> getPrefHMAP(int phase) {
		HashMap<String, Integer> HMAP = null;
		switch(phase){
			case 0:
				HMAP = getPrefHMAP0();
				break;
			case 1:
				HMAP = getPrefHMAP1();
				break;
			case 2:
				HMAP = getPrefHMAP2();
				break;
			default:
				HMAP = getPrefHMAP0();
		}
		return HMAP;
	}

	public HashMap<String, Double> getFreqHMAP(int phase) {
		HashMap<String, Double> HMAP = null;
		switch(phase){
			case 0:
				HMAP = getFreqHMAP0();
				break;
			case 1:
				HMAP = getFreqHMAP1();
				break;
			case 2:
				HMAP = getFreqHMAP2();
				break;
			default:
				HMAP = getFreqHMAP2();
		}
		return HMAP;
	}
	/* Fait la fusion de HMAP càd additionne les deux hmap en envoie les résultats dans le hmap mère*/

	public void fusion(HashMap<String, Integer> hm, int phase){
		HashMap<String, Integer> HMAP = getHMAP(phase);

		for(HashMap.Entry<String, Integer> entry : hm.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			HMAP.replace(key,HMAP.get(key) + value);
		}

	}

	public void fusion(Nucleotide tmp,int phase){
		fusion(tmp.getHMAP(phase),phase);
	}

	public void fusion(Nucleotide tmp){

		fusion(tmp.getHMAP0(),0);
		fusion(tmp.getHMAP1(),1);
		fusion(tmp.getHMAP2(),2);
	}
	public void fusionPref(HashMap<String, Integer> hm, int phase){
		HashMap<String, Integer> HMAP = getPrefHMAP(phase);

		for(HashMap.Entry<String, Integer> entry : hm.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			HMAP.replace(key,HMAP.get(key) + value);
		}

	}


	public void fusionPref(Nucleotide tmp){

		fusionPref(tmp.getPrefHMAP(0),0);
		fusionPref(tmp.getPrefHMAP(1),1);
		fusionPref(tmp.getPrefHMAP(2),2);
	}

	public String toString(){

		return "Nucleotide :  HMAP0\n"+ HMAP0.toString();
	}


	public void init(){
		//nothing
	}

	public void initPref(){
		prefHMAP0 = new HashMap<String,Integer>();
		prefHMAP1 = new HashMap<String,Integer>();
		prefHMAP2 = new HashMap<String,Integer>();
	}

	public void initFreq(){
		freqHMAP0 = new HashMap<String,Double>();
		freqHMAP1 = new HashMap<String,Double>();
		freqHMAP2 = new HashMap<String,Double>();
	}

	public void clear() {
		getHMAP0().clear();
		getHMAP1().clear();
		getHMAP2().clear();
		init();
	}


	public void calculPref(Nucleotide nucleotide) throws Exceptions.ExceptionCodonNotFound {
		HashMap<String, Integer> HMAP0 = nucleotide.getHMAP(0);
		HashMap<String, Integer> HMAP1 = nucleotide.getHMAP(1);
		HashMap<String, Integer> HMAP2 = nucleotide.getHMAP(2);
		HashMap<String, Integer> prefHMAP0 = getPrefHMAP(0);
		HashMap<String, Integer> prefHMAP1 = getPrefHMAP(1);
		HashMap<String, Integer> prefHMAP2 = getPrefHMAP(2);


		for(Map.Entry<String, Integer> entry : HMAP0.entrySet()) {
			String key = entry.getKey();
			Integer vHMAP0 = entry.getValue();
			if (HMAP1.getOrDefault(key, -1) != -1 && prefHMAP0.getOrDefault(key, -1) != -1) { // test useless mais au cas ou
				if(vHMAP0 != 0 && HMAP1.get(key) != 0 && HMAP2.get(key) !=0) {
					if (vHMAP0 >= HMAP1.get(key) && vHMAP0 >= HMAP2.get(key)) {
						// Pref0
						prefHMAP0.replace(key, 1 + prefHMAP0.get(key));
					}
					if (HMAP1.get(key) >= vHMAP0 && HMAP1.get(key) >= HMAP2.get(key)) {
						// Pref1
						prefHMAP1.replace(key, 1 + prefHMAP1.get(key));
					}
					if (HMAP2.get(key) >= vHMAP0 && HMAP2.get(key) >= HMAP2.get(key)) {
						// Pref2
						prefHMAP2.replace(key, 1 + prefHMAP2.get(key));
					}
				}
			} else {
				//throw new Exceptions.ExceptionCodonNotFound("Problem with pref phase"); //TODO UN BUG DE RESULTAT SI ON DECOMMENTE WHY ?
			}
		}
	}

	public void calculFreqPhase(int phase) throws Exceptions.ExceptionCodonNotFound {
		HashMap<String, Integer> HMAP = getHMAP(phase);
		HashMap<String, Double> freqHMAP = getFreqHMAP(phase);
		Double sum = 0.0;
		for(Map.Entry<String, Integer> entry : HMAP.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			sum += value;
		}
		for(Map.Entry<String, Integer> entry : HMAP.entrySet()) {
			String key = entry.getKey();
			Double value = Double.valueOf(entry.getValue());
			if (freqHMAP.getOrDefault(key, -1.0) != -1.0 ){
				freqHMAP.replace(key, (value/sum));
			}else{
				throw new Exceptions.ExceptionCodonNotFound("Problem with pref phase");
			}
		}
	}
	public void calculFreq() throws Exceptions.ExceptionCodonNotFound {
		calculFreqPhase(0);
		calculFreqPhase(1);
		calculFreqPhase(2);
	}


	public int sumNumberOfNucleotide(int phase){
		HashMap<String, Integer> HMAP = getHMAP(phase);
		int tmp = 0;
		for(Map.Entry<String, Integer> entry : HMAP.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			tmp += value;
		}

		return tmp;
	}

	public Double sumFreqOfNucleotide(int phase){
		HashMap<String, Double> HMAP = getFreqHMAP(phase);
		Double tmp = 0.;
		for(Map.Entry<String, Double> entry : HMAP.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			tmp += value;
		}

		return tmp;
	}

	public Integer sumPrefOfNucleotide(int phase){
		HashMap<String, Integer> HMAP = getPrefHMAP(phase);
		Integer tmp = 0;
		for(Map.Entry<String, Integer> entry : HMAP.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			tmp += value;
		}
		return tmp;
	}
}






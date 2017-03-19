package mainpackage;

import java.util.HashMap;
import java.util.Map;


public class Trinucleotide extends Nucleotide{
	// Un tableau de trinucléotide
	// (On le définit en dur car c'est plus optimal que de les générer) 
	
	
	public Trinucleotide(){
		init();
	}

	@Override
	public void init(){
		HashMap<String, Integer> HMAP0 = this.getHMAP0();
		HashMap<String, Integer> HMAP1 = this.getHMAP1();
		HashMap<String, Integer> HMAP2 = this.getHMAP2();


		for (String temp : Utils.getListOfTriNucleotide()) {
			HMAP0.put(temp, 0);
			HMAP1.put(temp, 0);
			HMAP2.put(temp, 0);
		}
	}

	public void initPref(){
		super.initPref();
		HashMap<String, Integer> PrefHMAP0 = this.getPrefHMAP0();
		HashMap<String, Integer> PrefHMAP1 = this.getPrefHMAP1();
		HashMap<String, Integer> PrefHMAP2 = this.getPrefHMAP2();


		for (String temp : Utils.getListOfTriNucleotide()) {
			PrefHMAP0.put(temp, 0);
			PrefHMAP1.put(temp, 0);
			PrefHMAP2.put(temp, 0);
		}
	}

	public void initFreq(){
		super.initFreq();
		HashMap<String, Double> FreqHMAP0 = this.getFreqHMAP0();
		HashMap<String, Double> FreqHMAP1 = this.getFreqHMAP1();
		HashMap<String, Double> FreqHMAP2 = this.getFreqHMAP2();


		for (String temp : Utils.getListOfTriNucleotide()) {
			FreqHMAP0.put(temp, 0.0);
			FreqHMAP1.put(temp, 0.0);
			FreqHMAP2.put(temp, 0.0);
		}
	}


	public void addTriN(String triN,int i, int phase){
		HashMap<String, Integer> HMAP = getHMAP(phase);
		HMAP.replace(triN,i+HMAP.get(triN));
	}
	


	public int countNumberOfTrinucleotide(int phase){
		HashMap<String, Integer> HMAP = getHMAP(phase);
		int tmp = 0;
		for(Map.Entry<String, Integer> entry : HMAP.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			tmp += value;
		}

		return tmp;
	}
	
	
}
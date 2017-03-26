package mainpackage;

import java.util.HashMap;

public class Dinucleotide extends Nucleotide{	
	// Un tableau de binucléotide 
	// (De même) 
	

	public Dinucleotide(){
		init();
	}
	@Override
	public void init(){
		HashMap<String, Integer> HMAP0 = this.getHMAP0();
		HashMap<String, Integer> HMAP1 = this.getHMAP1();
		HashMap<String, Integer> HMAP2 = this.getHMAP2();

		for (String temp : Utils.getListOfDiNucleotide()) {
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


		for (String temp : Utils.getListOfDiNucleotide()) {
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


		for (String temp : Utils.getListOfDiNucleotide()) {
			FreqHMAP0.put(temp, 0.0);
			FreqHMAP1.put(temp, 0.0);
			FreqHMAP2.put(temp, 0.0);
		}
	}


	public void addDiN(String din,int i, int phase){
		HashMap<String, Integer> HMAP = getHMAP(phase);
		HMAP.replace(din,i+HMAP.get(din));
	}
}

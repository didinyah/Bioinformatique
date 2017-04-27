package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

public class TraitementOrganisme {
	
	// lecture sans thread
	public static void lectureEtDL(ArrayList<Organism> listeOrga) {
		System.out.println(listeOrga.size());
		List<ArrayList<ResultData>> listResultData = new ArrayList<ArrayList<ResultData>>();
		int countEnd = 20; // mettre listeOrga.size() pour tout
		int countCurrent = 0;
		
		// Pour chaque organisme ajouté à la liste
		for(int i=0; i<countEnd; i++){
			Organism organism = listeOrga.get(i);
			String name = organism.getName();
			String replicons = organism.getReplicons().toString();
			ArrayList<ResultData> tmpListData = new ArrayList<ResultData>();
			
			// On regarde tous les NC_... qu'on a pour pouvoir les DL
			for (String key: organism.getReplicons().keySet()) {
			    //System.out.println("key : " + key);
				String valueID = organism.getReplicons().get(key);
			    //System.out.println(name + " " + valueID.toString());
			    
			    // FAIRE LES DL ICI
			    String url = "";
				try{
					long timeDebut = System.currentTimeMillis();
					url = Utils.DOWNLOAD_NC_URL.replaceAll("<ID>", valueID.toString());
					URL urlDL = new URL(url);
					BufferedReader in = new BufferedReader(new InputStreamReader(urlDL.openStream()));
					File f = new File(organism.getPath() + "\\" + organism.getName() + "_" + valueID + ".gb");
					
					
					
					
					// On lit le replicon
					System.out.println("Lecture du fichier : " + name + " " + valueID.toString());
					System.out.println(urlDL);
					
					ResultData rd = GestionFichier.read(in);
					
					long timeEnd = System.currentTimeMillis();
					int tailleFichier = rd.lineCount * 80; // taille du fichier = nb lignes * nb caractères par ligne
					double debit = tailleFichier / ((timeEnd - timeDebut)/1000.0); // o/s
					double debitKs = debit / 1024.0; // Ko/s
					System.out.println("Débit du fichier téléchargé : " + debitKs);
					System.out.println("temps pour l'analyse : " + (timeEnd-timeDebut));
					System.out.println("linecount : " + rd.lineCount);
					
					// Ligne suivante : créé les fichiers en brut avec la séquence complète
					FileUtils.copyURLToFile(urlDL, f);
					long timeEnd2 = System.currentTimeMillis();
					double debit2 = tailleFichier / ((timeEnd2 - timeEnd)/1000.0); // o/s
					double debit2Ks = debit2 / 1024.0; // Ko/s
					//System.out.println("Débit du fichier téléchargé : " + debitKs);
					System.out.println("temps pour le DL : " + debit2Ks);
					
					tmpListData.add(rd);
					
					//System.out.println(rd);
				}
				catch(Exception e){
					System.out.println(url);
					e.printStackTrace();
				}
				
			}
			countCurrent++;
			System.out.println(countCurrent);
			listResultData.add(tmpListData);
			//System.out.println(name + " " + replicons);
		}
		ResultData mergeTotal = new ResultData();
		for(ArrayList<ResultData>listtmp : listResultData) {
			// mergeRD additionne tous les chromosomes d'un seul organisme
			ResultData mergeRD = new ResultData();
			// listtmp = chromosome 1, chromosome 2, etc...
			mergeRD.fusions(listtmp);
			// création d'excel ici
			System.out.println(mergeRD);
			// fusion de fusion
			mergeTotal.fusion(mergeRD);
		}
		System.out.println(mergeTotal);
		System.out.println("fin de lecture et de DL");
	}
	
	// Exécution avec un fichier test
	public static void lectureTest() {
		String fichierTest = "files/chromosome_NC_015850.1.txt";
		ResultData rd = new ResultData();
		try {
			rd = GestionFichier.readWithFileName(fichierTest);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(rd);
		System.out.println("fin de lecture et de DL");
	}
	
	// Téléchargement des fichiers avec thread
	public static void lectureAvecThread(ArrayList<Organism> listeOrga, int nbThread) {
		ExecutorService executorService = Executors.newFixedThreadPool(nbThread);
		System.setProperty("https.protocols", "TLSv1.1");
		
		int countEnd = 15; // mettre listeOrga.size() pour tout
		int countCurrent = 0;
		
		// Pour chaque organisme ajouté à la liste
		for(int i=0; i<countEnd; i++){
			Organism organism = listeOrga.get(i);
			String name = organism.getName();
			String replicons = organism.getReplicons().toString();
			ArrayList<ResultData> tmpListData = new ArrayList<ResultData>();
			
			// On regarde tous les NC_... qu'on a pour pouvoir les DL
			for (String key: organism.getReplicons().keySet()) {
			    //System.out.println("key : " + key);
				String valueID = organism.getReplicons().get(key);
			    
			    // FAIRE LES DL ICI
			    String url = "";
				try{
					url = Utils.DOWNLOAD_NC_URL.replaceAll("<ID>", valueID.toString());
					URL urlDL = new URL(url);
					File f = new File(organism.getPath() + "\\" + organism.getName() + "_" + valueID + ".txt");

					// On télécharge le replicon
					System.out.println("Téléchargement du fichier : " + name + "_" + valueID.toString());
					
					// Ligne suivante : créé le fichiers en brut avec la séquence complète
					FileUtils.copyURLToFile(urlDL, f);
					
					// Lancement du thread d'analyse (suppression du fichier à la fin du thread)
					executorService.execute(new LancementAnalyse(urlDL, f, organism, key));
					
					//System.out.println(rd);
					
				}
				catch(Exception e){
					System.out.println(url);
					e.printStackTrace();
				}
				
			}
			System.out.println(countCurrent);
			countCurrent++;
			
		}
		executorService.shutdown();
		// On attend maintenant que toutes les analyses finissent (bloquant)
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Ici, tous les organismes ont leurs resultdata sur chacun de leurs replicons, on fait la somme de tout
		for(int i=0; i<countEnd; i++){
			Organism organism = listeOrga.get(i);
			ArrayList<ResultData> allDataOrga = summingResultsOrganism(organism);
			for(int j=0; j<allDataOrga.size(); j++) {
				System.out.println(allDataOrga.get(j));
			}
		}
		
		System.out.println("fin d'analyse avec thread");
	}
	
	public static ArrayList<ResultData> summingResultsOrganism(Organism organism) {
		
		ArrayList<ResultData> allResultData = new ArrayList<ResultData>();
		
		ArrayList<ResultData> listChloroplast = new ArrayList<ResultData>();
		ArrayList<ResultData> listChromosome = new ArrayList<ResultData>();
		ArrayList<ResultData> listDna = new ArrayList<ResultData>();
		ArrayList<ResultData> listPlasmid = new ArrayList<ResultData>();
		ArrayList<ResultData> listMitochondrion = new ArrayList<ResultData>();
		ArrayList<ResultData> listLinkage = new ArrayList<ResultData>();
		
		for(String key: organism.getRepliconsTraites().keySet()) {
			ResultData rd = organism.getRepliconsTraites().get(key);
			if(rd.isChloroplast()) {
				listChloroplast.add(rd);
			}
			else if(rd.isChromosome()) {
				listChromosome.add(rd);
			}
			else if(rd.isDna()) {
				listDna.add(rd);
			}
			else if(rd.isPlasmid()) {
				listPlasmid.add(rd);
			}
			else if(rd.isMitochondrion()) {
				listMitochondrion.add(rd);
			}
			else if(rd.isLinkage()) {
				listLinkage.add(rd);
			}
		}
		
		// On met les sommes de tout
		if(!listChloroplast.isEmpty()) {
			ResultData sumChloroplast = new ResultData();
			sumChloroplast.setName("Sum_Chloroplasts");
			sumChloroplast.fusions(listChloroplast);
			sumChloroplast.setChloroplast(true);
			allResultData.add(sumChloroplast);
		}
		if(!listChromosome.isEmpty()) {
			ResultData sumChromosome = new ResultData();
			sumChromosome.setName("Sum_Chromosomes");
			sumChromosome.fusions(listChromosome);
			sumChromosome.setChromosome(true);
			allResultData.add(sumChromosome);
		}
		if(!listDna.isEmpty()) {
			ResultData sumDna = new ResultData();
			sumDna.setName("Sum_DNA");
			sumDna.fusions(listDna);
			sumDna.setDna(true);
			allResultData.add(sumDna);
		}
		if(!listPlasmid.isEmpty()) {
			ResultData sumPlasmid = new ResultData();
			sumPlasmid.setName("Sum_Plasmids");
			sumPlasmid.fusions(listPlasmid);
			sumPlasmid.setPlasmid(true);
			allResultData.add(sumPlasmid);
		}
		if(!listMitochondrion.isEmpty()) {
			ResultData sumMitochondrion = new ResultData();
			sumMitochondrion.setName("Sum_Mitochondrions");
			sumMitochondrion.fusions(listMitochondrion);
			sumMitochondrion.setMitochondrion(true);
			allResultData.add(sumMitochondrion);
		}
		if(!listLinkage.isEmpty()) {
			ResultData sumLinkage = new ResultData();
			sumLinkage.setName("Sum_Linkages");
			sumLinkage.fusions(listLinkage);
			sumLinkage.setLinkage(true);
			allResultData.add(sumLinkage);
		}
		
		return allResultData;
	}
}

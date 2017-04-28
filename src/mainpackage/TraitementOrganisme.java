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

import mainpackage.Chargement.Chargement;

public class TraitementOrganisme {
	
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
	public static void DLAnalyseThread(ArrayList<Organism> listeOrga, int nbThread, Chargement charg) {
		ExecutorService executorService = Executors.newFixedThreadPool(nbThread);
		System.setProperty("https.protocols", "TLSv1.1");
		
		int countEnd = 10; // mettre listeOrga.size() pour tout
		int countCurrent = 0;
		
		charg.send("TELECHARGEMENT", countEnd);
		charg.send("ANALYSE", countEnd);
		
		// Pour chaque organisme ajouté à la liste
		for(int i=0; i<countEnd; i++){
			Organism organism = listeOrga.get(i);
			String name = organism.getName();
			
			int nbRepliconsDL=0;
			// On regarde tous les NC_... qu'on a pour pouvoir les DL
			for (String key: organism.getReplicons().keySet()) {
			    //System.out.println("key : " + key);
				String valueID = organism.getReplicons().get(key);
			    
			    // FAIRE LES DL ICI
			    String url = "";
				try{
					url = Utils.DOWNLOAD_NC_URL.replaceAll("<ID>", valueID.toString());
					URL urlDL = new URL(url);
					File f = new File(organism.getPath() + Configuration.DIR_SEPARATOR + organism.getName() + "_" + valueID + ".txt");

					// On télécharge le replicon
					System.out.println("Téléchargement du fichier : " + name + "_" + valueID.toString());
					
					// Ligne suivante : créé le fichiers en brut avec la séquence complète
					FileUtils.copyURLToFile(urlDL, f);
					
					// On envoie au chargement l'organisme si les replicons sont tous DL
					System.out.println("nb replicons org " + organism.getReplicons().keySet().size());
					System.out.println("nb replicons DL " + nbRepliconsDL);
					if(organism.getReplicons().keySet().size()-1 == nbRepliconsDL) {
						Organism orgTmp = new Organism();
						orgTmp.setKingdom("TELECHARGEMENT");
						orgTmp.setName(organism.getName());
						charg.send(orgTmp);
						System.out.println("BONJOUR");
					}
					
					// Lancement du thread d'analyse (suppression du fichier à la fin du thread)
					executorService.execute(new LancementAnalyse(f, organism, key, charg));
					
					//System.out.println(rd);
					nbRepliconsDL++;
					
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
		// FIN DU DL ET DE L'ANALYSE !
		
		// Ici, tous les organismes ont leurs resultdata sur chacun de leurs replicons, on fait la somme de tout
		for(int i=0; i<countEnd; i++){
			Organism organism = listeOrga.get(i);
			ArrayList<ResultData> allDataOrga = allResultsOrganism(organism);
			
			// TODO : Créer les excel ici !
			GestionExcel.CreateExcel(organism.getPath()+".xlsx", allDataOrga);
			
			for(int j=0; j<allDataOrga.size(); j++) {
				//System.out.println(allDataOrga.get(j));
			}
		}
		
		System.out.println("fin d'analyse avec thread");
	}
	
	public static ArrayList<ResultData> allResultsOrganism(Organism organism) {
		
		ArrayList<ResultData> allResultData = new ArrayList<ResultData>();
		
		ArrayList<ResultData> listChloroplast = new ArrayList<ResultData>();
		ArrayList<ResultData> listChromosome = new ArrayList<ResultData>();
		ArrayList<ResultData> listDna = new ArrayList<ResultData>();
		ArrayList<ResultData> listPlasmid = new ArrayList<ResultData>();
		ArrayList<ResultData> listMitochondrion = new ArrayList<ResultData>();
		ArrayList<ResultData> listLinkage = new ArrayList<ResultData>();
		
		for(String key: organism.getRepliconsTraites().keySet()) {
			ResultData rd = organism.getRepliconsTraites().get(key);
			//System.out.println(rd);
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
		
		// ResultData avec les infos de base (1 organisme)
		ResultData rdGeneralInformation = Utils.setGeneralInformationRD(1, listChromosome.size(), listPlasmid.size(), listDna.size(), organism.getModificationDate());
		allResultData.add(rdGeneralInformation);
		
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
		
		// Pour avoir toutes les sommes et les replicons de base on doit les ajouter aussi à la liste
		for(String key: organism.getRepliconsTraites().keySet()) {
			ResultData rd = organism.getRepliconsTraites().get(key);
			allResultData.add(rd);
		}
		
		return allResultData;
	}
}

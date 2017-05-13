package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import Windows.JCheckBoxTree;
import Windows.TreeWindow;
import mainpackage.Chargement.Chargement;

public class TraitementOrganisme {
	
	// Exï¿½cution avec un fichier test
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
	
	// Tï¿½lï¿½chargement des fichiers avec thread
	public static void DLAnalyseThread(ArrayList<Organism> listeOrga, int nbThread, Chargement charg, JCheckBoxTree tree) {
		ExecutorService executorService = Executors.newFixedThreadPool(nbThread);
		System.setProperty("https.protocols", "TLSv1.1");
		
		int countEnd = listeOrga.size(); // mettre listeOrga.size() pour tout
		int countCurrent = 0;
		
		charg.send("TELECHARGEMENT", countEnd);
		charg.send("ANALYSE", countEnd);
		
		// Pour chaque organisme ajoutï¿½ ï¿½ la liste
		for(int i=0; i<countEnd; i++){
			Organism organism = listeOrga.get(i);
			String name = organism.getName();
			
			// si on a dï¿½jï¿½ le fichier de l'organisme, on le DL pas
			File orgExcel = new File(organism.getPath()+".xlsx");
			String dateOrganisme =  organism.getModificationDate();
			String dateExcel =  organism.getModificationDate();
			if(orgExcel.exists())
			{
				// si le fichier est corrompu, on doit le reDL
				try {
					dateExcel = GestionExcel.GetLastModificationDate(orgExcel.getPath());
				}
				catch(Exception e) {
					e.printStackTrace();
					dateExcel = "RedownloadCarCorrompu";
					charg.log("Fichier " + orgExcel.getName() + " corrompu, retelechargement");
				}
			}
			if(!orgExcel.exists() || !dateExcel.equals(dateOrganisme)) {
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

						// On tï¿½lï¿½charge le replicon
						System.out.println("Telechargement du fichier : " + name + "_" + key.toString());
						
						// Ligne suivante : crï¿½ï¿½ le fichiers en brut avec la sï¿½quence complï¿½te
						FileUtils.copyURLToFile(urlDL, f);
						
						// On envoie au chargement l'organisme si les replicons sont tous DL
						System.out.println("nb replicons org " + organism.getReplicons().keySet().size());
						System.out.println("nb replicons DL " + nbRepliconsDL);
						if(organism.getReplicons().keySet().size()-1 == nbRepliconsDL) {
							Organism orgTmp = new Organism();
							orgTmp.setKingdom("TELECHARGEMENT");
							orgTmp.setName(organism.getName());
							charg.send(orgTmp);
						}
						
						// Lancement du thread d'analyse (suppression du fichier ï¿½ la fin du thread)
						executorService.execute(new LancementAnalyse(f, organism, key, charg));
						
						//System.out.println(rd);
						nbRepliconsDL++;
						
					}
					catch(Exception e){
						System.out.println(url);
						e.printStackTrace();
					}
					
				}
			}
			else {
				charg.send(1); // on considï¿½re qu'on envoie le tï¿½lï¿½chargement et le virus de l'organisme si on a pas ï¿½ le faire
				//charg.send(1);
				charg.log(orgExcel.getName() +" existe deja et n'est pas a mettre a jour.");
			}
			// On crï¿½ï¿½ l'archive contenant toutes les sï¿½quences de l'organisme si l'user le veut
			if(Configuration.OPTION_ARCHIVE_FILES) {
				Utils.ZipFiles(organism.getPath());
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
		System.out.println("fin d'analyse avec thread");
		
		// Ici, tous les organismes ont leurs resultdata sur chacun de leurs replicons, on fait la somme de tout
		
		// Au cas où il y a un souci, on refait l'opération des totaux 2-3 fois
		boolean toutOK = true;
		int tries = 1;
		while(toutOK || tries <= Configuration.MAX_TRIES_TOTAL_RESULTS) {
			HashMap<String, ArrayList<ResultData>> mapSubGroupResult = new HashMap<String, ArrayList<ResultData>>();
			HashMap<String, ArrayList<ResultData>> mapGroupResult = new HashMap<String, ArrayList<ResultData>>();
			HashMap<String, ArrayList<ResultData>> mapKingdomResult = new HashMap<String, ArrayList<ResultData>>();
			
			for(int i=0; i<countEnd; i++){
				Organism organism = listeOrga.get(i);
				//ArrayList<ResultData> allDataOrga = allResultsOrganism(organism);
				ArrayList<ResultData> allDataOrga = new ArrayList<ResultData>();
				File orgExcel = new File(organism.getPath()+".xlsx");
				if(orgExcel.exists())
				{
					// si le fichier est corrompu, on ne fait pas planter le calcul
					try {
						allDataOrga = GestionExcel.GetFromExcel(orgExcel.getAbsolutePath());
					}
					catch(Exception e) {
						e.printStackTrace();
						toutOK = false;
					}
				}
				
				// une fois zippï¿½, si l'user ne veut pas conserver les fichiers txt, on les supprime
				if(!Configuration.OPTION_DL_KEEPFILES) {
					File file = new File(organism.getPath()+ Configuration.DIR_SEPARATOR);
					try {
						FileUtils.deleteDirectory(file);
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				// Crï¿½er les excel ici
				//System.out.println(allDataOrga.get(0).toString());
				//GestionExcel.CreateExcel(organism.getPath()+".xlsx", allDataOrga);
				
				String subgroupOrg = organism.getSubgroup();
				String groupOrg = organism.getGroup();
				String kingdomOrg = organism.getKingdom();
				
				// si le sous groupe n'est pas connu, alors on l'ajoute au map, sinon on additionne les donnï¿½es des rï¿½sultdata dï¿½jï¿½ existants
				if(mapSubGroupResult.get(subgroupOrg) == null) {
					mapSubGroupResult.put(subgroupOrg, allDataOrga);
				}
				else {
					mapSubGroupResult.put(subgroupOrg, addResultsTotal(mapSubGroupResult.get(subgroupOrg), allDataOrga, subgroupOrg));
				}
				
				if(mapGroupResult.get(groupOrg) == null) {
					mapGroupResult.put(groupOrg, allDataOrga);
				}
				else {
					mapGroupResult.put(groupOrg, addResultsTotal(mapGroupResult.get(groupOrg), allDataOrga, groupOrg));
				}
				
				if(mapKingdomResult.get(kingdomOrg) == null) {
					mapKingdomResult.put(kingdomOrg, allDataOrga);
				}
				else {
					mapKingdomResult.put(kingdomOrg, addResultsTotal(mapKingdomResult.get(kingdomOrg), allDataOrga, kingdomOrg));
				}
				
				for(int j=0; j<allDataOrga.size(); j++) {
					//System.out.println(allDataOrga.get(j));
				}
			}
			
			// Maintenant on crï¿½ï¿½ les excel totaux
			// total_subgroup
			for(String key: mapSubGroupResult.keySet()) {
				ArrayList<ResultData> allRd = mapSubGroupResult.get(key);
				GestionExcel.CreateExcel(Configuration.RESULTS_FOLDER + Configuration.DIR_SEPARATOR + "Total_" + key +".xlsx", allRd);
			}
			// total_group
			for(String key: mapGroupResult.keySet()) {
				ArrayList<ResultData> allRd = mapGroupResult.get(key);
				GestionExcel.CreateExcel(Configuration.RESULTS_FOLDER + Configuration.DIR_SEPARATOR + "Total_" + key +".xlsx", allRd);
			}
			// total_kingdom
			for(String key: mapKingdomResult.keySet()) {
				ArrayList<ResultData> allRd = mapKingdomResult.get(key);
				GestionExcel.CreateExcel(Configuration.RESULTS_FOLDER + Configuration.DIR_SEPARATOR + "Total_" + key +".xlsx", allRd);
			}
			
			System.out.println("essai de total num " + tries);
			tries++;
		}
		
		
		System.out.println("fin des sommes des resultats");
		
		// On affiche maintenant la fenetre pour consulter les excel
		TreeWindow.displayTreeWindow(tree);
	}
	
	public static ArrayList<ResultData> allResultsOrganism(Organism organism) {
		
		ArrayList<ResultData> allResultData = new ArrayList<ResultData>();
		
		ArrayList<ResultData> listChloroplast = new ArrayList<ResultData>();
		ArrayList<ResultData> listChromosome = new ArrayList<ResultData>();
		ArrayList<ResultData> listDna = new ArrayList<ResultData>();
		ArrayList<ResultData> listPlasmid = new ArrayList<ResultData>();
		ArrayList<ResultData> listMitochondrion = new ArrayList<ResultData>();
		ArrayList<ResultData> listLinkage = new ArrayList<ResultData>();
		
		int nbCDSTotal = 0;
		int nbCDSInvalidTotal = 0;
		
		for(String key: organism.getRepliconsTraites().keySet()) {
			ResultData rd = organism.getRepliconsTraites().get(key);
			rd.setOrganismName(organism.getName());
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
			nbCDSTotal += rd.getNumberCdsSeq();
			nbCDSInvalidTotal += rd.getNumberCdsSeqInvalid();
		}
		
		// ResultData avec les infos de base (1 organisme)
		ResultData rdGeneralInformation = Utils.setGeneralInformationRD(1, listChromosome.size(), listPlasmid.size(), listDna.size(), organism.getModificationDate(), organism.getName(), nbCDSTotal, nbCDSInvalidTotal);
		//System.out.println(rdGeneralInformation);
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
		
		// Pour avoir toutes les sommes et les replicons de base on doit les ajouter aussi ï¿½ la liste
		for(String key: organism.getRepliconsTraites().keySet()) {
			ResultData rd = organism.getRepliconsTraites().get(key);
			allResultData.add(rd);
		}
		
		return allResultData;
	}
	
	// fonction utilisée SEULEMENT POUR FAIRE LES TOTAUX pour récupérer les infos d'un organisme 
	public static ArrayList<ResultData> allResultsOrganismExcel(Organism organism) {
		
		ArrayList<ResultData> allResultData = new ArrayList<ResultData>();
		
		ResultData listChloroplast = new ResultData();
		ResultData listChromosome = new ResultData();
		ResultData listDna = new ResultData();
		ResultData listPlasmid = new ResultData();
		ResultData listMitochondrion = new ResultData();
		ResultData listLinkage = new ResultData();
		
		
		File orgExcel = new File(organism.getPath()+".xlsx");
		String dateOrganisme =  organism.getModificationDate();
		int nbChromosome = 0;
		int nbPlasmid = 0;
		int nbDna = 0;
		String lastModifDate = "";
		int nbOrganism = 0;
		int nbCDSTotal = 0;
		int nbCDSInvalidTotal = 0;
		if(orgExcel.exists())
		{
			// si le fichier est corrompu, pas d'erreur bloquante
			
			// parcourir toutes les feuilles de l'excel et récuperer les sum et general information
			try {
				dateOrganisme = GestionExcel.GetLastModificationDate(orgExcel.getPath());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		// ResultData avec les infos de base (1 organisme)
		/*ResultData rdGeneralInformation = Utils.setGeneralInformationRD(1, listChromosome.size(), listPlasmid.size(), listDna.size(), organism.getModificationDate(), organism.getName(), nbCDSTotal, nbCDSInvalidTotal);
		//System.out.println(rdGeneralInformation);
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
		}*/
		
		return allResultData;
	}
	
public static ArrayList<ResultData> addResultsTotal(ArrayList<ResultData> rd1, ArrayList<ResultData> rd2, String totalName) {
		
		ArrayList<ResultData> allResultData = new ArrayList<ResultData>();
		
		ArrayList<ResultData> listChloroplast = new ArrayList<ResultData>();
		ArrayList<ResultData> listChromosome = new ArrayList<ResultData>();
		ArrayList<ResultData> listDna = new ArrayList<ResultData>();
		ArrayList<ResultData> listPlasmid = new ArrayList<ResultData>();
		ArrayList<ResultData> listMitochondrion = new ArrayList<ResultData>();
		ArrayList<ResultData> listLinkage = new ArrayList<ResultData>();
		int nbChromosome = 0;
		int nbPlasmid = 0;
		int nbDna = 0;
		String lastModifDate = "";
		int nbOrganism = 0;
		int nbCDSTotal = 0;
		int nbCDSInvalidTotal = 0;
		
		
		for(ResultData rd: rd1) {
			if(rd.getName().equals("General_Information")) {
				nbChromosome = rd.getNbChromosome();
				nbPlasmid = rd.getNbPlasmid();
				nbDna = rd.getNbDna();
				if(rd.getLastModifDate().compareTo(lastModifDate) >1) {
					lastModifDate = rd.getLastModifDate();
				};
				nbOrganism = rd.getNbOrganism();
				nbCDSTotal = rd.getNumberCdsSeq();
				nbCDSInvalidTotal = rd.getNumberCdsSeqInvalid();
			}
			else if(rd.getName().equals("Sum_Chloroplasts")) {
				listChloroplast.add(rd);
			}
			else if(rd.getName().equals("Sum_Chromosomes")) {
				listChromosome.add(rd);
			}
			else if(rd.getName().equals("Sum_DNA")) {
				listDna.add(rd);
			}
			else if(rd.getName().equals("Sum_Plasmids")) {
				listPlasmid.add(rd);
			}
			else if(rd.getName().equals("Sum_Mitochondrions")) {
				listMitochondrion.add(rd);
			}
			else if(rd.getName().equals("Sum_Linkages")) {
				listLinkage.add(rd);
			}
		}
		
		for(ResultData rd: rd2) {
			if(rd.getName().equals("Sum_Chloroplasts")) {
				listChloroplast.add(rd);
			}
			else if(rd.getName().equals("Sum_Chromosomes")) {
				listChromosome.add(rd);
			}
			else if(rd.getName().equals("Sum_DNA")) {
				listDna.add(rd);
			}
			else if(rd.getName().equals("Sum_Plasmids")) {
				listPlasmid.add(rd);
			}
			else if(rd.getName().equals("Sum_Mitochondrions")) {
				listMitochondrion.add(rd);
			}
			else if(rd.getName().equals("Sum_Linkages")) {
				listLinkage.add(rd);
			}
			else if(rd.getName().equals("General_Information")) {
				nbChromosome += rd.getNbChromosome();
				nbPlasmid += rd.getNbPlasmid();
				nbDna += rd.getNbDna();
				if(rd.getLastModifDate().compareTo(lastModifDate) >1) {
					lastModifDate = rd.getLastModifDate();
				};
				nbOrganism += rd.getNbOrganism();
				nbCDSTotal += rd.getNumberCdsSeq();
				nbCDSInvalidTotal += rd.getNumberCdsSeqInvalid();
			}
		}
		
		// ResultData avec les infos de base
		ResultData rdGeneralInformation = Utils.setGeneralInformationRD(nbOrganism, nbChromosome, nbPlasmid, nbDna, lastModifDate, totalName, nbCDSTotal, nbCDSInvalidTotal );
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
		
		
		return allResultData;
	}
}

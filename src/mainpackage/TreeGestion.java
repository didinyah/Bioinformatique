package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.commons.io.FileUtils;

import com.google.common.util.concurrent.ServiceManager;

import Windows.JCheckBoxTree;
import mainpackage.TreeBuilder.OrganismType;
import mainpackage.Chargement.Chargement;


public class TreeGestion {
	
	private static ArrayList<Organism> listeOrga = new ArrayList<Organism>();
	
	public static JCheckBoxTree construct(Chargement charg){
		List<TreeBuilder> services = new ArrayList<TreeBuilder>();
		charg.send("EUKARYOTES", 25);
		charg.send("PROKARYOTES", 70);
		charg.send("VIRUSES", 200);
		TreeBuilder eukaryotes = new TreeBuilder(OrganismType.EUKARYOTES, charg);
		TreeBuilder prokaryotes = new TreeBuilder(OrganismType.PROKARYOTES, charg);
		TreeBuilder viruses = new TreeBuilder(OrganismType.VIRUSES, charg);
		services.add(eukaryotes);
		services.add(prokaryotes);
		services.add(viruses);
		
		
		
		ServiceManager sm = new ServiceManager(services);
		sm.startAsync();
		sm.awaitStopped();
		
		List<Organism> organisms = new ArrayList<Organism>();
		organisms.addAll(eukaryotes.organisms());
		organisms.addAll(prokaryotes.organisms());
		organisms.addAll(viruses.organisms());
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Genomes");
		
		
		
		int count = 0;
		int countPro = 0;
		int countEuk = 0;
		int countVir = 0;
		ArrayList<String> groupesEuk = new ArrayList<String>();
		ArrayList<String> groupesPro = new ArrayList<String>();
		ArrayList<String> groupesVir = new ArrayList<String>();
		for(Organism o : organisms){
			o.updateTree(rootNode);
			listeOrga.add(o);
			count++;
			if(o.getKingdom()=="EUKARYOTES") {
				countEuk++;
				if(!groupesEuk.contains(o.getGroup()))
					groupesEuk.add(o.getGroup());
			}
			else if(o.getKingdom()=="PROKARYOTES") {
				countPro++;
				if(!groupesPro.contains(o.getGroup()))
					groupesPro.add(o.getGroup());
			}
			else if(o.getKingdom()=="VIRUSES") {
				countVir++;
				if(!groupesVir.contains(o.getGroup()))
					groupesVir.add(o.getGroup());
			}
		}
		
		// 2 pages de chaque 
		System.out.println("count = " +count);
		System.out.println("countEuk = " +countEuk);
		System.out.println("countPro = " +countPro);
		System.out.println("countVir = " +countVir);
		for(int i=0; i<groupesEuk.size(); i++) {
			System.out.println(groupesEuk.get(i));
		}
		
		
		JCheckBoxTree mainTree = new JCheckBoxTree();
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		mainTree.setModel(treeModel);
		return mainTree;
	}
	
	// Fonction temporaire pour voir le contenu de l'arbre
	private static String getTreeText(TreeModel model, Object object, String indent) {
	    String myRow = indent + object + "\n";
	    for (int i = 0; i < model.getChildCount(object); i++) {
	        myRow += getTreeText(model, model.getChild(object, i), indent + "-");
	    }
	    return myRow;
	}
	
	private static void lectureEtDL() {
		System.out.println(listeOrga.size());
		List<ArrayList<ResultData>> listResultData = new ArrayList<ArrayList<ResultData>>();
		int countEnd = 50;
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
	
	private static void lectureTest() {
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
	
	public static void main(String[] args) throws Exception {
		int nbOrgaEnTout = 295;
		//Chargement charg = new Chargement(3);
		//JCheckBoxTree test = TreeGestion.construct(charg);
		
		//TreeModel model = test.getModel();
		
		//lectureEtDL();
		lectureTest();
		//System.out.println(getTreeText(model, model.getRoot(), ""));
	}
	
	

	
}
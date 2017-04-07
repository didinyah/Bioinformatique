package mainpackage;

import java.io.File;
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
		
		// Pour chaque organisme ajouté à la liste
		for(int i=0; i<listeOrga.size(); i++){
			Organism organism = listeOrga.get(i);
			String name = organism.getName();
			String replicons = organism.getReplicons().toString();
			
			// On regarde tous les NC_... qu'on a pour pouvoir les DL
			for (String key: organism.getReplicons().keySet()) {
			    //System.out.println("key : " + key);
				String valueID = organism.getReplicons().get(key);
			    //System.out.println(name + " " + valueID.toString());
			    
			    // FAIRE LES DL ICI
			    String url = "";
				try{
					url = Utils.DOWNLOAD_NC_URL.replaceAll("<ID>", valueID.toString());
					URL urlDL = new URL(url);
					File f = new File(organism.getPath() + "\\" + organism.getName() + "_" + valueID + ".gb");
					
					// Ligne suivante : créé les fichiers en brut avec la séquence complète
					FileUtils.copyURLToFile(urlDL, f);
				}
				catch(Exception e){
					System.out.println(url);
					e.printStackTrace();
				}
				
			}

			//System.out.println(name + " " + replicons);
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nbOrgaEnTout = 295;
		Chargement charg = new Chargement(3);
		JCheckBoxTree test = TreeGestion.construct(charg);
		
		TreeModel model = test.getModel();
		
		//lectureEtDL();
		//System.out.println(getTreeText(model, model.getRoot(), ""));
	}
	
	

	
}
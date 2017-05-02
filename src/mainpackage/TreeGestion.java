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
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.commons.io.FileUtils;

import com.google.common.util.concurrent.ServiceManager;

import Windows.JCheckBoxTree;
import mainpackage.TreeBuilder.OrganismType;
import mainpackage.Chargement.Chargement;


public class TreeGestion {
	
	private ArrayList<Organism> listeOrga = new ArrayList<Organism>();
	
	private Chargement charg;
	
	public TreeGestion() {
		
	}
	
	public JCheckBoxTree construct(Chargement charg, int nb_euk, int nb_pro, int nb_vir){
		List<TreeBuilder> services = new ArrayList<TreeBuilder>();
		
		TreeBuilder eukaryotes = null;
		TreeBuilder prokaryotes = null;
		TreeBuilder viruses = null;
		if(Configuration.OPTION_DL_EUKARYOTES) {
			charg.send("EUKARYOTES", nb_euk);
			eukaryotes = new TreeBuilder(OrganismType.EUKARYOTES, charg);
			services.add(eukaryotes);
		}
		if(Configuration.OPTION_DL_PROKARYOTES) {
			charg.send("PROKARYOTES", nb_pro);
			prokaryotes = new TreeBuilder(OrganismType.PROKARYOTES, charg);
			services.add(prokaryotes);
		}
		if(Configuration.OPTION_DL_VIRUSES) {
			charg.send("VIRUSES", nb_vir);
			viruses = new TreeBuilder(OrganismType.VIRUSES, charg);
			services.add(viruses);
		}
		
		ServiceManager sm = new ServiceManager(services);
		sm.startAsync();
		sm.awaitStopped();

		
		List<Organism> organisms = new ArrayList<Organism>();
		if(Configuration.OPTION_DL_EUKARYOTES) {
			organisms.addAll(eukaryotes.organisms());
		}
		if(Configuration.OPTION_DL_PROKARYOTES) {
			organisms.addAll(prokaryotes.organisms());
		}
		if(Configuration.OPTION_DL_VIRUSES) {
			organisms.addAll(viruses.organisms());
		}
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Results");
		
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
		
		this.charg = charg;
		return mainTree;
	}
	
	public ArrayList<Organism> getListOrganism() {
		return listeOrga;
	}
	
	public Chargement getChargement() {
		return this.charg;
	}
	
	// Fonction temporaire pour voir le contenu de l'arbre
	/*private static String getTreeText(TreeModel model, Object object, String indent) {
	    String myRow = indent + object + "\n";
	    for (int i = 0; i < model.getChildCount(object); i++) {
	        myRow += getTreeText(model, model.getChild(object, i), indent + "-");
	    }
	    return myRow;
	}*/
	
	public static void main(String[] args) throws Exception {
		
		
		//TreeModel model = test.getModel();
		//System.out.println(getTreeText(model, model.getRoot(), ""));
	}
	
	

	
}
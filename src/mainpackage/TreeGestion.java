package mainpackage;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.google.common.util.concurrent.ServiceManager;

import Windows.JCheckBoxTree;
import mainpackage.TreeBuilder.OrganismType;


public class TreeGestion {
	
	public static JCheckBoxTree construct(){
		List<TreeBuilder> services = new ArrayList<TreeBuilder>();
		TreeBuilder eukaryotes = new TreeBuilder(OrganismType.EUKARYOTES);
		TreeBuilder prokaryotes = new TreeBuilder(OrganismType.PROKARYOTES);
		TreeBuilder viruses = new TreeBuilder(OrganismType.VIRUSES);
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
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		
		JCheckBoxTree mainTree = new JCheckBoxTree();
		
		for(Organism o : organisms){
			o.updateTree(mainTree);
		}

		return mainTree;
	}
	
	public static void main(String[] args) throws Exception {
		JCheckBoxTree test = TreeGestion.construct();
		TreeModel model = test.getModel();
		System.out.println(getTreeText(model, model.getRoot(), ""));
	}
	
	

	private static String getTreeText(TreeModel model, Object object, String indent) {
	    String myRow = indent + object + "\n";
	    for (int i = 0; i < model.getChildCount(object); i++) {
	        myRow += getTreeText(model, model.getChild(object, i), indent + "-");
	    }
	    return myRow;
	}
}
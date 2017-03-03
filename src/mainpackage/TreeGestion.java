package mainpackage;

import java.util.ArrayList;
import java.util.List;

import com.google.common.util.concurrent.ServiceManager;

import mainpackage.TreeBuilder.OrganismType;


public class TreeGestion {
	
	public static Tree construct(){
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
		
		Tree mainTree = new Tree<Tree>();
		
		for(Organism o : organisms){
			o.updateTree(mainTree);
		}

		return mainTree;
	}
	
	public static void main(String[] args) throws Exception {
		Tree test = TreeGestion.construct();
	    test.printTree();
	}
}
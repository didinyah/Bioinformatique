package mainpackage;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import Windows.JCheckBoxTree;


public class Organism {
	private String kingdom;
	private String group;
	private String subgroup;
	private String name;
	private String creation_date;
	private String modification_date;
	private HashMap<String, String> replicons;
	private HashMap<String, ResultData> replicons_traites;
	private boolean activated;
	private String base_folder = "C:\\tmpBio\\results\\"; // on stocke les donn�es ici pour l'instant
	
	public Organism(String kingdom, String group, String subgroup, String name, String creation_date, String modification_date){
		this.kingdom = kingdom.replace("/", "_").replace(" ", "_");
		this.group = group.replace("/", "_").replace(" ", "_");
		this.subgroup = subgroup.replace("/", "_").replace(" ", "_");
		this.name = name.replace("/", "_").replace(" ", "_");
		this.creation_date = creation_date;
		this.modification_date = modification_date;
		this.replicons = new HashMap<String, String>();
		this.replicons_traites = new HashMap<String, ResultData>();
		this.activated = true;
	}
	
	public Organism(){
		this.replicons = new HashMap<String, String>();
		this.replicons_traites = new HashMap<String, ResultData>();
		this.activated = true;
	}
	
	public boolean addReplicon(String name, String id){
		if(this.replicons.containsKey(name)){
			return false;
		} else {
			this.replicons.put(name, id);
			return true;
		}
	}
	
	public void updateTree(DefaultMutableTreeNode mainT){
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)mainT.getRoot();
		
		//Récupération ou création du royaume
		DefaultMutableTreeNode kingdomT = Organism.containsNode(root, this.kingdom);
		if(kingdomT == null){
			kingdomT = new DefaultMutableTreeNode(this.kingdom);
			root.add(kingdomT);
		}

		//Récupération ou création du groupe
		DefaultMutableTreeNode groupT = Organism.containsNode(kingdomT, this.group);
		if(groupT == null){
			groupT = new DefaultMutableTreeNode(this.group);
			kingdomT.add(groupT);
		}
		
		//Récupération ou création du sous-groupe
		DefaultMutableTreeNode subgroupT = Organism.containsNode(groupT, this.subgroup);
		if(subgroupT == null){
			subgroupT = new DefaultMutableTreeNode(this.subgroup);
			groupT.add(subgroupT);
		}
		
		DefaultMutableTreeNode nameOrg = new DefaultMutableTreeNode(this.name);
		subgroupT.add(nameOrg);
	}
	
	public static DefaultMutableTreeNode containsNode(DefaultMutableTreeNode level, String nodeString) {
    	DefaultMutableTreeNode res = null;
    	
        for (Enumeration e = level.breadthFirstEnumeration(); e.hasMoreElements() && res == null;) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.toString().equals(nodeString)) {
                res = node;
            }
        }
        return res;
    }

	@Override
	public String toString(){
		
		String str = this.kingdom+"/"+this.group+"/"+this.subgroup+"/"+this.name;
		str += "\nReplicons :";
		for(String name : this.replicons.keySet()){
			str += "\n - "+name+" - "+this.replicons.get(name);
		}
		return str;
	}
	

	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom.replace("/", "_").replace(" ", "_");
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group.replace("/", "_").replace(" ", "_");
	}

	public String getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(String subgroup) {
		this.subgroup = subgroup.replace("/", "_").replace(" ", "_");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.replace("/", "_").replace(" ", "_");
	}


	public String getCreationDate() {
		return creation_date;
	}

	public void setCreationDate(String creation_date) {
		this.creation_date = creation_date;
	}

	public String getModificationDate() {
		return modification_date;
	}

	public void setModificationDate(String modification_date) {
		this.modification_date = modification_date;
	}

	public HashMap<String, String> getReplicons() {
		return replicons;
	}

	public void setReplicons(HashMap<String, String> replicons) {
		this.replicons = replicons;
	}
	
	public void setActivated(boolean a){
		this.activated = a;
	}
	
	public boolean getActivated(){
		return this.activated;
	}
	
	public int size(){
		return this.activated ? this.replicons.size() : 0;
	}
	
	public HashMap<String, ResultData> getRepliconsTraites() {
		return replicons_traites;
	}

	public void setRepliconsTraites(HashMap<String, ResultData> repliconsTraites) {
		this.replicons_traites = repliconsTraites;
	}
	
	public void removeReplicons(ArrayList<String> replicons){
		for(String replicon : replicons){
			this.replicons.remove(replicon);
		}
	}
	
	public String getPath(){
		// Construction de la chaine de charactere
		String cur = base_folder;
		cur += "\\"+this.getKingdom();
		cur += "\\"+this.getGroup();
		cur += "\\"+this.getSubgroup();
		cur += "\\"+this.getName();
		
		return cur;
	}
	
	public boolean createPath(){
		String path = this.getPath();
		// Cr�ation du dossier
		
		/*AccessManager.accessFile(path);
		File p = new File(path);
		
		if(p.exists() && p.isDirectory()) {
			// Si le dossier existe d�j�
			AccessManager.doneWithFile(path);
			return true;
		}else{
			// Si le fichier n'existe pas
			boolean ok = p.mkdirs();
			AccessManager.doneWithFile(path);
			return ok;
		}*/
		return false;
	}
	
}

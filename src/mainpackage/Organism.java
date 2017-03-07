package mainpackage;

import java.util.ArrayList;
import java.util.HashMap;


public class Organism {
	private String kingdom;
	private String group;
	private String subgroup;
	private String name;
	private String creation_date;
	private String modification_date;
	private HashMap<String, String> replicons;
	private ArrayList<String> replicons_traites;
	private boolean activated;
	
	public Organism(String kingdom, String group, String subgroup, String name, String creation_date, String modification_date){
		this.kingdom = kingdom.replace("/", "_").replace(" ", "_");
		this.group = group.replace("/", "_").replace(" ", "_");
		this.subgroup = subgroup.replace("/", "_").replace(" ", "_");
		this.name = name.replace("/", "_").replace(" ", "_");
		this.creation_date = creation_date;
		this.modification_date = modification_date;
		this.replicons = new HashMap<String, String>();
		this.replicons_traites = new ArrayList<String>();
		this.activated = true;
	}
	
	public Organism(){
		this.replicons = new HashMap<String, String>();
		this.replicons_traites = new ArrayList<String>();
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
	
	// Cr�� l'arborescence avec les organismes trait�s (kingom, group et subgroup)
	
	public void updateTree(Tree mainT){
		Tree kingdomT;
		Tree groupT;
		Tree subgroupT;
		if(mainT.contains(this.kingdom)){
			kingdomT = (Tree)mainT.get(this.kingdom);
		} else {
			kingdomT = new Tree<Tree>();
			mainT.add(this.kingdom, kingdomT);
		}
		
		if(kingdomT.contains(this.group)){
			groupT = (Tree)kingdomT.get(this.group);
		} else {
			groupT = new Tree<Tree>();
			kingdomT.add(this.group, groupT);
		}
		
		if(groupT.contains(this.subgroup)){
			subgroupT = (Tree)groupT.get(this.subgroup);
		} else {
			subgroupT = new Tree<Organism>();
			groupT.add(this.subgroup, subgroupT);
		}
		
		subgroupT.add(this.name, this);
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
	
	public void addRepliconTraite(String replicon){
		this.replicons_traites.add(replicon);
	}
	
	public ArrayList<String> getProcessedReplicons(){
		return this.replicons_traites;
	}
	
	public void removeReplicons(ArrayList<String> replicons){
		for(String replicon : replicons){
			this.replicons.remove(replicon);
		}
	}
	
}
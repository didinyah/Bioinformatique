package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

import mainpackage.Chargement.Chargement;

// La classe suivante va se lancer dans plusieurs threads, elle se fait � la fin d'un DL de fichier pour analyse
public class LancementAnalyse implements Runnable {

	public File file;
	public Organism organism;
	public String keyReplicon;
	public Chargement charg;
	
    public LancementAnalyse(File f, Organism o, String key, Chargement charg) 
    { 
    	file = f;
    	organism = o;
    	keyReplicon = key;
    	this.charg = charg;
    }
    
	public void run() {
		ResultData rd = new ResultData();
		try {
			rd = GestionFichier.readWithFileName(file.getAbsolutePath());
			System.out.println("Bonjour c'est moi le thread ! Je viens d'analyser " + file.getName());
			//System.out.println(rd);
			
			// Ajout du ResultData obtenu � l'organisme et ajout des donn�es utiles � partir de l'organisme
			rd.setName(keyReplicon);
			HashMap<String, ResultData> hash = organism.getRepliconsTraites();
			hash.put(keyReplicon, rd);
			organism.setRepliconsTraites(hash);
			
			// On envoie au chargement l'organisme si les replicons sont tous DL
			if(organism.getReplicons().keySet().size() == organism.getRepliconsTraites().keySet().size()) {
				Organism orgTmp = new Organism();
				orgTmp.setKingdom("ANALYSE");
				orgTmp.setName(organism.getName());
				charg.send(orgTmp);
				// si tous les replicons sont DL de l'organisme, on créé l'excel
				ArrayList<ResultData> allDataOrga = TraitementOrganisme.allResultsOrganism(organism);
				
				GestionExcel.CreateExcel(organism.getPath()+".xlsx", allDataOrga);
			}
			
			// Suppression du fichier
			if(!Configuration.OPTION_DL_KEEPFILES && !Configuration.OPTION_ARCHIVE_FILES) {
				if(file.delete()){
	    			// System.out.println(file.getName() + " est supprim� !");
	    		}
				else{
	    			System.out.println("La suppression a �chou� " + file.getName());
	    		}
			}
		} 
		catch (IOException e) {
			System.out.println("Erreur de thread pour le fichier " + file.getName());
			e.printStackTrace();
		}
	}

}

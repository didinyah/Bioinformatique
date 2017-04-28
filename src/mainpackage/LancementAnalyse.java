package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;

// La classe suivante va se lancer dans plusieurs threads, elle se fait � la fin d'un DL de fichier pour analyse
public class LancementAnalyse implements Runnable {

	public File file;
	public Organism organism;
	public String keyReplicon;
	
    public LancementAnalyse(File f, Organism o, String key) 
    { 
    	file = f;
    	organism = o;
    	keyReplicon = key;
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
			
			// Suppression du fichier
			boolean delete_file_option = false;
			if(delete_file_option) {
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

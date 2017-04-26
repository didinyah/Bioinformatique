package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

// La classe suivante va se lancer dans plusieurs threads, elle se fait à la fin d'un DL de fichier pour analyse
public class LancementAnalyse implements Runnable {

	public URL urlDL;
	public File file;
	
    public LancementAnalyse(URL url, File f) 
    { 
    	urlDL = url;
    	file = f;
    }
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(urlDL.openStream()));
			ResultData rd = GestionFichier.readWithFileName(file.getAbsolutePath());
			System.out.println("Bonjour c'est moi le thread ! Je viens d'analyser " + file.getName());
			//System.out.println(rd);
			if(file.delete()){
    			//System.out.println(file.getName() + " est supprimé !");
    		}
			else{
    			System.out.println("La suppression a échoué " + file.getName());
    		}
		} 
		catch (IOException e) {
			System.out.println("Erreur de thread pour le fichier " + file.getName());
			e.printStackTrace();
		}
		
	}

}

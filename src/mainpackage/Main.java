package mainpackage;

import Windows.JCheckBoxTree;
import Windows.MainWindow;
import mainpackage.Chargement.Chargement;

/*
 * Classe Main
 * Classe principale qui charge l'interface et lance les tests
 */
public class Main {
	/*
	 * Main
	 * String[] args : arguments du programme (non utilisÃ©)
	 * Description : fonction principale du programme qui appelle l'interface 
	 * et lance les tests
	 * Return : void
	 */
	public static void main(String[] args) {
		/* 
		String osUtilise = System.getProperty("os.name");
		if(osUtilise.contains("windows")) {
			Configuration.DIR_SEPARATOR = "\\";
		}
		*/
		/*
		int nbOrgaEnTout = 289 + 10*2; // nombre d'orga + nb d'analyses et nb de téléchargements
		int nbThread = 10;
		Chargement charg = new Chargement(5, nbOrgaEnTout);
		TreeGestion t = new TreeGestion();
		JCheckBoxTree tree = t.construct(charg);

		//TraitementOrganisme.lectureEtDL(t.getListOrganism());
		//TraitementOrganisme.lectureTest();
		TraitementOrganisme.DLAnalyseThread(t.getListOrganism(), nbThread, t.getChargement(), tree);
		*/
		
		MainWindow.displayMainWindow();
	}

}

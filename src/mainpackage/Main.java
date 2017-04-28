package mainpackage;

import Windows.JCheckBoxTree;
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
		String osUtilise = System.getProperty("os.name");
		System.out.println("avant test OS" + Configuration.DIR_SEPARATOR);
		if(osUtilise.contains("windows")) {
			Configuration.DIR_SEPARATOR = "\\";
		}
		System.out.println("après test OS" + Configuration.DIR_SEPARATOR);
		int nbOrgaEnTout = 295;
		int nbThread = 10;
		Chargement charg = new Chargement(3);
		TreeGestion t = new TreeGestion();
		JCheckBoxTree tree = t.construct(charg);

		//TraitementOrganisme.lectureEtDL(t.getListOrganism());
		//TraitementOrganisme.lectureTest();
		TraitementOrganisme.DLAnalyseThread(t.getListOrganism(), nbThread);
	}

}

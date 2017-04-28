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
	 * String[] args : arguments du programme (non utilisé)
	 * Description : fonction principale du programme qui appelle l'interface 
	 * et lance les tests
	 * Return : void
	 */
	public static void main(String[] args) {
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

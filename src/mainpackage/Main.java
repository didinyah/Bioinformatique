package mainpackage;
import mainpackage.Dinucleotide;
import mainpackage.Trinucleotide;

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
		//loadInterface();
		//testNucleotide();
		System.out.println("Hello World");
		//load data
		// test
		Trinucleotide tridata = new Trinucleotide();
		System.out.println(tridata.GetHMAP().size());
		Dinucleotide didata = new Dinucleotide();
		System.out.println(didata.GetHMAP().size());
	}

}

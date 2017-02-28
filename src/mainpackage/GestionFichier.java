package mainpackage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class GestionFichier {

	public static final String FILENAME_SIMPLE = "files/test/simple.txt";

	public static void readFileByLine(String fileName){

		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}

	public static void readFileByChar(String fileName){

		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			// on reconstruit la ligne ?
			String reconstructLine = "";

			br = new BufferedReader(new FileReader(fileName));

			int r;
			while ((r = br.read()) != -1) {
				char ch = (char) r;
				if(ch != '\n'){

					reconstructLine += ch;
				}
				else
				{
					// Attention n'affiche pas la dernière ligne si il n'y a pas de \n (mais bon c'est juste un test)
					System.out.println(reconstructLine);
					reconstructLine = "";
				}

			}


		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}



	public static void read(String fileName){

		// Logique du code
		// On lit un fichier on sait au début, --> on est dans l'entête (on crée un dijoncteur HEADER = 1)
		// (optionel) on vérife avec des mots clè qu'on est dans l'entête au cas ou
		// pendant la phase d'en tête (HEADER = 1) on extracte toutes les infos importants
		// TODO cds list (ANALYZER)
		// ... (à vérifier avec l'enonce) (ANALYZER)
		// On trouve ORIGIN implique (HEADER = 0) (CONTENT = 1)
		// pendant la phase de lecture (CONTENT=1) on check si on est dans un cds ou pas
		// une fois trouvé ! (et verifier)
		// TODO on calcul avec les phases 0 1 2  (ANALYZER)
		// on ressort 3 HMAP (ou on additione à un HMAP global)
		// TODO on calcul les pref  (ANALYZER)
		// Si on stock tout les HMAP on peut faire post processing
		// Sinon on ajoute dans un HMAP des pref +1 si la condition est verifié (voir énoncé)
		// on resort du cds
		// on continue et on refait la même chose si on retrouve un cds  sinon :
		// On sort de la lecture (CONTENT = 0) et on reva dans un en tête (HEADER = 1) (il faut trouver un connecteur de fin)
		// puis on recommence jusqu'à la fin de la lecture du fichier
		// résultat on aura 3 HMAP pour phase 0 1 2 et 3 HMAP de pref 0 1 2


		// DE MÊME AVEC LES DINUCLEOTIDE !!!!


		// ***************************************

		// List disjoncteur

		boolean HEADER = true;
		boolean CONTENT = false;
		boolean CDS_MULTI_LINE = false;


		BufferedReader br = null;
		FileReader fr = null;

		try {
			// On lit un fichier on sait au début, --> on est dans l'entête (on crée un dijoncteur HEADER = 1)
			// (optionel) on vérife avec des mots clè qu'on est dans l'entête au cas ou
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			// on reconstruit la ligne ?
			String reconstructLine = "";
			List<String> multiLine = new ArrayList<>();

			// compte TODO delete count in prod
			int content_line = 0;
			int header_line = 0;
			int block_transition = 0;
			int cds_count = 0;
			int cds_multi_line_count = 0;
			int max_cds_size = 0;

			br = new BufferedReader(new FileReader(fileName));

			int r;
			while ((r = br.read()) != -1) {
				char ch = (char) r;
				if(ch != '\n'){
					reconstructLine += ch;
				}
				else
				{
					// Attention n'affiche pas la dernière ligne si il n'y a pas de \n (mais bon c'est juste un test)
					//System.out.println(reconstructLine);

					if(Analyzer.checkInit(reconstructLine) && HEADER){ // TODO checkInit -> isInit
						// PHASE CONTENT
						HEADER = false;
						CONTENT = true;

					}else if(Analyzer.checkEnd((reconstructLine)) && CONTENT){ // Todo checkEnd -> isEnd
						// PHASE HEADER
						HEADER = true;
						CONTENT = false;
						block_transition += 1;

					}


					if(HEADER && !CONTENT){
						// pendant la phase d'en tête (HEADER = 1) on extracte toutes les infos importants
						// TODO cds list (ANALYZER)
						// ... (à vérifier avec l'enonce) (ANALYZER)
						header_line += 1;
						if(Analyzer.checkCds(reconstructLine)){
							cds_count += 1;
							if(Analyzer.isCdsMultiLine(reconstructLine)){
								cds_multi_line_count += 1;
								CDS_MULTI_LINE = true;
							}else{
								// Todo push cds data
							}
						}

						//cette partie gère les cds multi line
						if(CDS_MULTI_LINE && !Analyzer.isEndCdsMultiLine(reconstructLine)){
							multiLine.add(reconstructLine); // cela ajoute le premier jusqu'à l'avant dernier
						}else if(CDS_MULTI_LINE)
						{
							// on ajoute le dernier (important)
							multiLine.add(reconstructLine);
							//System.out.println("* cds multi line size:"+multiLine.size());
							//System.out.println(multiLine);
							max_cds_size = Math.max(max_cds_size,multiLine.size());

							CDS_MULTI_LINE = false;
							try {
								String tmpcds = Analyzer.cdsMultiLineToString(multiLine);
								System.out.println(tmpcds);
							} catch (Exception e) {
								// TODO gestion de l'erreur
								e.printStackTrace();
							}
							multiLine.clear();
							// Todo push cds data
						}



					}
					else if(CONTENT && !HEADER)
					{
						// TODO on calcul avec les phases 0 1 2  (ANALYZER)
						// on ressort 3 HMAP (ou on additione à un HMAP global)
						// TODO on calcul les pref  (ANALYZER)
						// Si on stock tout les HMAP on peut faire post processing
						// Sinon on ajoute dans un HMAP des pref +1 si la condition est verifié (voir énoncé)
						content_line += 1;
					}
					reconstructLine = "";
				}

			}

			// après la fin de la boucle

			System.out.println("BlockTransition detected:" + block_transition);
			System.out.println("Header line:" + header_line);
			System.out.println("Content line:" + content_line);
			System.out.println("cds count:" + cds_count);
			System.out.println("\tcds multi line count :"+cds_multi_line_count);
			System.out.println("\tmax multi cds size :"+max_cds_size);

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();


			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}
	
	public static void readCSVFile(String filename) {
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(filename));
		    String[] nextLine = reader.readNext();
		    String[] nextLine2 = reader.readNext();
		    
		    for(int i =0; i<nextLine.length; i++) {
		    	System.out.println(nextLine[i]+ "|||" + nextLine2[i]);
		    }
		    //while ((nextLine = reader.readNext()) != null) {
			    /* INFO EUKARYOTES
			     * nextLine[0] = Nom de l'organisme
			     * nextLine[4] = Groupe de l'organisme
			     * nextLine[5] = Sous-groupe de l'organisme
			     * nextLine[6] = Taille en Mb
			     * nextLine[9] = A REGARDER POUR CHECK SI Y A NC
			     * nextLine[12] = Nombre de genes
			     * nextLine[14] = Date de sortie
			     * nextLine[15] = Date de modification
			     * nextLine[17] = Lien FTP du refseq
			     * nextLine[18] = Lien FTP du genbank
			     */
			    
			    // On v�rifie que c'est bien un �l�ment que l'on veut
		    	
			    if(nextLine[9].contains("NC")) {
			    	System.out.println(nextLine[17]);
			    }
		       
		    //}
		    
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) throws IOException {
		String eukaryotes = "files/genomes_euks.csv";
		String organelles = "files/genomes_organelles.csv";
		String plasmids = "files/genomes_plasmids.csv";
		String prokaryotes = "files/genomes_proks.csv";
		String viruses = "files/genomes_viruses.csv";
		readCSVFile(viruses);
	}
}

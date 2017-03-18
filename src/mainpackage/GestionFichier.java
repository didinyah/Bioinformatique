package mainpackage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			String sCurrentLine = "";

			br = new BufferedReader(new FileReader(fileName));

			int r;
			while ((r = br.read()) != -1) {
				char ch = (char) r;
				if(ch != '\n'){

					sCurrentLine += ch;
				}
				else
				{
					// Attention n'affiche pas la dernière ligne si il n'y a pas de \n (mais bon c'est juste un test)
					System.out.println(sCurrentLine);
					sCurrentLine = "";
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



	public static void read(String fileName) throws IOException {

		// Logique du code
		// On lit un fichier on sait au début, --> on est dans l'entête (on crée un dijoncteur HEADER = 1)
		// (optionel) on vérife avec des mots clè qu'on est dans l'entête au cas ou
		// pendant la phase d'en tête (HEADER = 1) on extracte toutes les infos importants
		//  on obtient cds list (ANALYZER)
		// ... (à vérifier avec l'enonce) (ANALYZER)
		// On trouve ORIGIN implique (HEADER = 0) (CONTENT = 1)
		// pendant la phase de lecture (CONTENT=1) on check si on est dans un cds ou pas
		// une fois trouvé ! (et verifier)
		// on calcul avec les phases 0 1 2  (ANALYZER)
		// on ressort 3 HMAP (ou on additione à un HMAP global)
		// TODO on calcul les pref  (ANALYZER)
		// Si on stock tout les HMAP on peut faire post processing
		// Sinon on ajoute dans un HMAP des pref +1 si la condition est verifié (voir énoncé)
		// on resort du cds
		// on continue et on refait la même chose si on retrouve un cds  sinon :
		// On sort de la lecture (CONTENT = 0) et on reva dans un en tête (HEADER = 1) (il faut trouver un connecteur de fin)
		// puis on recommence jusqu'à la fin de la lecture du fichier
		// résultat on aura 3 HMAP pour phase 0 1 2 et 3 HMAP de pref 0 1 2


		// TODO DE MÊME AVEC LES DINUCLEOTIDE !!!!


		// ***************************************

		// List disjoncteur

		boolean HEADER = true;
		boolean CONTENT = false;
		boolean CDS_MULTI_LINE = false;



		BufferedReader br = null;
		FileReader fr = null;

		//Tableau de ligne pour la reconstruction
		List<String> multiLine = new ArrayList<String>();

		// CDS
		Bornes cdsInHeader= new Bornes();
		HashMap<Bornes.Borne,Boolean> multiLineOnCds = new HashMap<Bornes.Borne,Boolean>();
		HashMap<Bornes.Borne,String> multipleCdsStr = null;

		//Trinucléotide var
		Trinucleotide tttGeneral = new Trinucleotide();

		// important var
		int contentCount = 1; // Nombre de lettre (init à 1 car on considère qu'on a lu la première)


		// compte TODO delete count in prod ou le sortir en bilan

		//****************************************
		//**    COMPTEUR  						**
		//****************************************
		int content_line = 0;
		int header_line = 0;
		int block_transition = 0;
		int cds_count = 0;
		int cds_multi_line_count = 0;
		int max_cds_size = 0;
		int fail_cds = 0;
		int fail_content_line =0;
		int fail_codon = 0;
		int cds_complement = 0;
		int cds_complement_fail = 0;
		int boucle_count = 0;
		int line_count = 0;

		br = new BufferedReader(new FileReader(fileName));
		String sCurrentLine;

		//****************************************
		//**    BOUCLE DE LECTURE DU FICHIER    **
		//****************************************
		while ((sCurrentLine = br.readLine()) != null) {
			boucle_count++;
			line_count++;
			if(Analyzer.checkInit(sCurrentLine) && HEADER){ // TODO checkInit -> isInit

				//****************************************
				//**    PHASE  DE TRANSITION CONTENT	**
				//****************************************
				HEADER = false;
				CONTENT = false;

			}else if(Analyzer.checkEnd((sCurrentLine)) && CONTENT){ // Todo checkEnd -> isEnd

				//****************************************
				//**    PHASE  DE TRANSITION HEADER		**
				//****************************************
				HEADER = true;
				CONTENT = false;
				block_transition += 1;
				// On vide les cds
				cdsInHeader.clear();
				multiLineOnCds.clear();
				multipleCdsStr.clear();
				contentCount = 1; // On reset le compteur
			}

			//********************************
			//**    HEADER ANALYSER  		**
			//********************************
			if(HEADER && !CONTENT){
				// pendant la phase d'en tête (HEADER = 1) on extracte toutes les infos importants
				// TODO cds list (ANALYZER)
				// ... (à vérifier avec l'enonce) (ANALYZER)
				header_line += 1;
				if(Analyzer.checkCds(sCurrentLine)){
					cds_count += 1;
					if(Analyzer.isCdsMultiLine(sCurrentLine)){
						cds_multi_line_count += 1;
						CDS_MULTI_LINE = true;
					}else{

						try {
							cdsInHeader.fusion(Analyzer.cdsToBornes(sCurrentLine));
						} catch (Exceptions.ExceptionCds | Exceptions.ExceptionBorne exceptionCds) {
							//exceptionCds.printStackTrace(); // TODO check erreur
							//System.out.println(sCurrentLine);
							fail_cds += 1;
						}
					}
				}

				//*************************************
				//**    GESTION DES CDS MULTI LINE   **
				//*************************************
				if(CDS_MULTI_LINE && !Analyzer.isEndCdsMultiLine(sCurrentLine)){
					multiLine.add(sCurrentLine); // cela ajoute le premier jusqu'à l'avant dernier
				}else if(CDS_MULTI_LINE)
				{
					// on ajoute le dernier (important)
					multiLine.add(sCurrentLine);

					max_cds_size = Math.max(max_cds_size,multiLine.size());

					CDS_MULTI_LINE = false;
					try {
						String tmpcds = Analyzer.cdsMultiLineToString(multiLine);
						cdsInHeader.fusion(Analyzer.cdsToBornes(tmpcds));

					} catch (Exceptions.ExceptionCds exceptionCds) {
						//exceptionCds.printStackTrace(); // TODO check erreur
						fail_cds += 1;
					} catch (Exceptions.ExceptionBorne exceptionBorne) {
						exceptionBorne.printStackTrace(); // todo ? (je crois que c'est un cas assez rare)
					} catch (Exception e) {
						e.printStackTrace();
					}
					multiLine.clear();
				}



			}

			//****************************************
			//**    CONTENT ANALYSE					**
			//****************************************
			else if(CONTENT && !HEADER)
			{
				//  on calcul avec les phases 0 1 2  (ANALYZER)
				// on ressort 3 HMAP (ou on additione à un HMAP global)
				// TODO on calcul les pref  (ANALYZER)
				// Si on stock tout les HMAP on peut faire post processing
				// Sinon on ajoute dans un HMAP des pref +1 si la condition est verifié (voir énoncé)


				// Recupère toutes les bornes qui commencent et terminent sur cette ligne (la ligne courrante)

				List<Bornes.Borne> sousStartBornes = cdsInHeader.extractStartBornesFromLine(contentCount,contentCount+60);
				List<Bornes.Borne> sousEndBornes = cdsInHeader.extractEndBornesFromLine(contentCount,contentCount+60);

				//****************************
				//**    Début du CDS		**
				//****************************
				for(Bornes.Borne bStart : sousStartBornes){
					boucle_count++;
					bStart.setTemporyLineBornInf(contentCount); // on stock l'info pour le découpage
					multiLineOnCds.put(bStart,true);

				}
				//********************************
				//**    Pendant les CDS			**
				//********************************


				// TODO PAS OPTIMALE RAJOUTE ENORMEMENT DE COMPLEXITE (minime peut être mais en comptage de boucle enorme 75 fois plus par exemple)
				for(Map.Entry<Bornes.Borne, Boolean> entry : multiLineOnCds.entrySet()) {
					boucle_count++;
					Bornes.Borne b = entry.getKey();
					Boolean disj = entry.getValue();
					if(disj){
						// La ligne fait parti d'un cds (de la borne key)
						try {
							// on rajoute les lignes
							multipleCdsStr.put(b,multipleCdsStr.get(b) + Analyzer.extractContentLine(sCurrentLine) );
						} catch (Exceptions.ExceptionPatternLine exceptionPatternLine) {
							fail_content_line+=1;
						}
					}
				}



				//****************************
				//**    Fin du CDS		    **
				//****************************
				for(Bornes.Borne bEnd : sousEndBornes){
					boucle_count++;
					//********************************
					//**    RECONSTRUCTION DE LIGNE **
					//********************************
					bEnd.setTemporyLineBornSup(contentCount); // on stock l'info pour le découpage (peut être inutile)
					Integer decoupeInf = bEnd.getBorninf() -  bEnd.getTemporyLineBornInf(); // TODO VERIF borne
					Integer decoupeSuf = bEnd.getBornsup() + decoupeInf - bEnd.getBorninf()+1; // TODO VERIF borne


					String str = multipleCdsStr.get(bEnd).substring(decoupeInf,decoupeSuf); // on découpe
					String strToCount = "";


					//*********************************
					//**    Jointure et complement	 **
					//*********************************
					if(bEnd.isMultipleBorne() && bEnd.isLastMultipleBorne() ){

						String tmp = "";
						for(Map.Entry<Bornes.Borne, String> entry : multipleCdsStr.entrySet()) {
							boucle_count++;
							Bornes.Borne b = entry.getKey();
							String st = entry.getValue();
							if(bEnd.getLinkId().equals(b.getLinkId()) ){ // On regarde si ils ont le même id => jointure
								tmp += st;
							}
						}

						if(bEnd.isComplement()){
							strToCount = Analyzer.complement(tmp);
							cds_complement++;
						}else{
							strToCount = tmp;
						}


					}else{
						if(bEnd.isComplement()){
							strToCount = Analyzer.complement(str);
							cds_complement++;
						}else{
							strToCount = str;
						}
					}

					//****************************
					//**    COMPTAGE		    **
					//****************************
					if(!strToCount.equals("")){
						try {

							Analyzer.countTrinIn3PhasesFromString(strToCount,tttGeneral);
						} catch (Exceptions.ExceptionCodonNotFound e) {

							fail_codon += 1;
							if(bEnd.isComplement()){
								cds_complement_fail++;
							}
						} catch (Exceptions.ExceptionPatternLine exceptionPatternLine) {
							fail_content_line++;
							//exceptionPatternLine.printStackTrace();
						}
					}


					multiLineOnCds.remove(bEnd);

					// On delete le texte pour soulager la ram
					if(!bEnd.isMultipleBorne()) {
						multipleCdsStr.remove(bEnd);
					}
				}

				contentCount += 60;
				content_line += 1;
			}
			else if(!CONTENT && !HEADER){

				//****************************************
				//**    PHASE  DE TRANSITION (ORIGIN)   **
				//****************************************
				// reconstruLine vaut ORIGIN (ici)
				CONTENT = true; // On  passe en mode Content à la prochain ligne;


				// Permettra de reconstruire les cds pour faire les calculs
				multipleCdsStr = cdsInHeader.initMultipleCdsStr();


			}

		}

		//***************************
		//**    FIN DE BOUCLE	   **
		//***************************
		System.out.println("Line count:"+line_count);
		System.out.println("Boucle count :"+boucle_count);
		System.out.println("\t ratio :"+boucle_count/(line_count));
		System.out.println("BlockTransition detected:" + block_transition);
		System.out.println("Header line:" + header_line);
		System.out.println("Content line:" + content_line);
		System.out.println("cds count:" + cds_count);
		System.out.println("\tcds complement count :"+cds_complement);
		System.out.println("\tcds multi line count :"+cds_multi_line_count);
		System.out.println("\tmax multi cds size :"+max_cds_size);
		System.out.println("\tcds extractor fail :"+fail_cds);
		System.out.println("\tcds complement fail :"+cds_complement_fail);
		System.out.println("Content (pattern or letter bug) cds fail :"+fail_content_line);
		System.out.println("Codon init or end from cds fail :"+fail_codon);
		System.out.println(tttGeneral.getHMAP0());
		System.out.println(tttGeneral.getHMAP1());
		System.out.println(tttGeneral.getHMAP2());


		//****************************************
		//**    FERMETURE DU FICHIER		    **
		//****************************************


		if (br != null)
			br.close();

		if (fr != null)
			fr.close();

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

package mainpackage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.sun.org.apache.regexp.internal.RE;

public class GestionFichier {



	public static ResultData readWithFileName(String fileName) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		return read(br);
	}


	public static ResultData read(BufferedReader br) throws IOException {

		// ***************************************

		// List disjoncteur

		boolean HEADER = true;
		boolean CONTENT = false;
		boolean CDS_MULTI_LINE = false;
		boolean HEADER_FEATURE = false;

		//Tableau de ligne pour la reconstruction
		List<String> multiLine = new ArrayList<String>();

		// CDS
		Bornes cdsInHeader= new Bornes();
		HashMap<Bornes.Borne,Boolean> multiLineOnCds = new HashMap<Bornes.Borne,Boolean>();
		HashMap<Bornes.Borne,String> multipleCdsStr =  new HashMap<Bornes.Borne,String>();

		//Trinucléotide var
		Trinucleotide tttGeneral = new Trinucleotide();
		Trinucleotide tttCurrentCds = new Trinucleotide();
		// Initialisation des pref
        tttGeneral.initPref();
        tttGeneral.initFreq(); // (peut être mit autre part)

		//Dinucléotide var
		Dinucleotide ddGeneral = new Dinucleotide();
		Dinucleotide ddCurrentCds = new Dinucleotide();
		//Initialisation des pref
		ddGeneral.initPref();
		ddGeneral.initFreq();

		// important var
		int contentCount = 1; // Nombre de lettre (init à 1 car on considère qu'on a lu la première)



		//****************************************
		//**    COMPTEUR  						**
		//****************************************
		int content_line = 0;
		int header_line = 0;
		int block_transition = 0;
		int cds_count = 0;
		int cds_multi_line_count = 0;
		int fail_cds = 0;
		int fail_content_line =0;
		int fail_codon = 0;
		int cds_complement = 0;
		int cds_complement_fail = 0;
		int boucle_count = 0;
		int line_count = 0;

		String sCurrentLine;

		//****************************************
		//**    BOUCLE DE LECTURE DU FICHIER    **
		//****************************************
		while ((sCurrentLine = br.readLine()) != null) {
			boucle_count++;
			line_count++;
			if(Analyzer.checkInit(sCurrentLine) && HEADER){

				//****************************************
				//**    PHASE  DE TRANSITION CONTENT	**
				//****************************************
				HEADER = false;
				CONTENT = false;
				HEADER_FEATURE = false;

			}else if(Analyzer.checkEnd((sCurrentLine)) && CONTENT){

				//****************************************
				//**    PHASE  DE TRANSITION HEADER		**
				//****************************************
				HEADER = true;
				CONTENT = false;
				HEADER_FEATURE = false;
				// On additione les Trinucleotide

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
			if(HEADER && !CONTENT && HEADER_FEATURE){
				// pendant la phase d'en tête (HEADER = 1) on extracte toutes les infos importants
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
						} catch (Exceptions.ExceptionCds e){
							fail_cds++;
						} catch (Exceptions.ExceptionBorne exceptionCds){
							fail_cds++;
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


					CDS_MULTI_LINE = false;
					try {
						String tmpcds = Analyzer.cdsMultiLineToString(multiLine);
						cdsInHeader.fusion(Analyzer.cdsToBornes(tmpcds));

					} catch (Exceptions.ExceptionCds ex)
					{
						fail_cds++;
					}
					catch (Exceptions.ExceptionPatternLine ex)
					{
						fail_cds++;
					}
					catch(Exceptions.ExceptionBorne exceptionCds) {
						fail_cds++;
					}
					multiLine.clear();
				}



			}
			//********************************
			//**    FEATURE HEADER ANALYZER **
			//********************************
			else if (HEADER && !CONTENT && !HEADER_FEATURE){

				if(Analyzer.isFeatureLine(sCurrentLine)){
					HEADER_FEATURE = true;
				}
			}
			//****************************************
			//**    CONTENT ANALYSE					**
			//****************************************
			else if(CONTENT && !HEADER)
			{
				//  on calcul avec les phases 0 1 2  (ANALYZER)
				// on ressort 3 HMAP (ou on additione à un HMAP global)
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


				for(Map.Entry<Bornes.Borne, Boolean> entry : multiLineOnCds.entrySet()) {
					boucle_count++;
					Bornes.Borne b = entry.getKey();
					Boolean disj = entry.getValue();
					if(disj){
						// La ligne fait parti d'un cds (de la borne key)
						try {

							multipleCdsStr.put(b, multipleCdsStr.getOrDefault(b,"") + Analyzer.extractContentLine(sCurrentLine) );
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
					Integer decoupeInf = bEnd.getBorninf() -  bEnd.getTemporyLineBornInf(); // borne semble correcte
					Integer decoupeSuf = bEnd.getBornsup() + decoupeInf - bEnd.getBorninf()+1; // aussi


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
							Analyzer.countTrinIn3PhasesFromString(strToCount,tttGeneral,tttCurrentCds);
							tttGeneral.calculPref(tttCurrentCds);
							Analyzer.countDinIn2PhasesFromString(strToCount,ddGeneral,ddCurrentCds);
							//ddGeneral.calculPref(ddCurrentCds); TODO il parait qu'il ne faut pas le faire
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

					//System.out.println(cdsInHeader.size()); TODO VOIR ICI POUR OPTIMISATION PROBLEM
					multiLineOnCds.remove(bEnd);
                    tttCurrentCds.clear();
                    ddCurrentCds.clear();
					// On delete le texte pour soulager la ram
					if(!bEnd.isMultipleBorne()) {
						multipleCdsStr.remove(bEnd);
						cdsInHeader.removeBorn(bEnd);
					}else if (bEnd.isMultipleBorne() && bEnd.isLastMultipleBorne()){

						// Todo voir si la suppression peut être fait autrement
						List<Bornes.Borne> tmplist = new ArrayList<Bornes.Borne>();
						for(Map.Entry<Bornes.Borne, String> entry : multipleCdsStr.entrySet()) {
							boucle_count++;
							Bornes.Borne b = entry.getKey();
							String st = entry.getValue();
							if(bEnd.getLinkId().equals(b.getLinkId()) ){ // On regarde si ils ont le même id => jointure
								tmplist.add(b);
							}
						}

						for(Bornes.Borne b : tmplist){
							boucle_count++;
							multipleCdsStr.remove(b);
							cdsInHeader.removeBorn(b);
						}

						tmplist.clear();

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
				//multipleCdsStr = cdsInHeader.initMultipleCdsStr();


			}

		}

		//***************************
		//**    FIN DE BOUCLE	   **
		//***************************
		ResultData result = new ResultData();
		result.lineCount = line_count;
		result.headerLine = header_line;
		result.contentLine = content_line;
		result.numberCdsSeq = cds_count;
		result.numberCdsSeqInvalid = fail_cds; // TODO VERIF ! il semble avoir un bug
		result.cdsMultiLine = cds_multi_line_count;
		result.cdsBorneComplement = cds_complement;

		result.blockTransition = block_transition;
		result.borneComplementFail = cds_complement_fail;
		result.inBorneContentFail = fail_content_line;
		result.codonOfBornFail = fail_codon;
		try {
			tttGeneral.calculFreq();
			ddGeneral.calculFreq();
		} catch (Exceptions.ExceptionCodonNotFound exceptionCodonNotFound) {
			exceptionCodonNotFound.printStackTrace();
		}


		result.ttt = tttGeneral;
		result.dd = ddGeneral;

		System.out.println("Boucle count :"+boucle_count);
		System.out.println("\t ratio :"+boucle_count/(line_count));

        //****************************************
		//**    FERMETURE DU FICHIER		    **
		//****************************************


		if (br != null)
			br.close();

		return result;

	}

	public static void main(String[] args) throws IOException {

	}
}

package mainpackage;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;*/
import org.apache.poi.xssf.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor.RED;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GestionExcel 
{
	// TYPES DE CELL :HSSFCell.[CELL_TYPE_FORMULA, CELL_TYPE_NUMERIC, ou CELL_TYPE_STRING]
	
	private enum Phase { Phase0, FreqPhase0, Phase1, FreqPhase1, Phase2, FreqPhase2, PrefPhase0, PrefPhase1, PrefPhase2};
	private String[] onglet = {"Sum_Chromosome","Sum_Mitochondrion", "Sum_Chloroplast", "Sum_Plasmid", "Sum_DNA", "Sum_Linkage", "completeGenome"};
	private static XSSFWorkbook wb;
	
	//Crée un fichier excel vide
	public static void CreationFichierVide(String file)
	{
		FileOutputStream fileOut;
		try 
		{
			fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close(); 
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void CreationFichier(String file/*, XSSFWorkbook wb*/)
	{
		FileOutputStream fileOut;
		try 
		{
			File f = new File(file);
			f.getParentFile().mkdirs();
			fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
			System.out.println("Le fichier " + file + "a ete cree.");
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void SupprimerFichier(String fichier)
	{
		try
		{
			File file = new File(fichier);

			if(file.delete())
			{
				System.out.println(file.getName() + " a ete supprime!");
			}
			else
			{
				throw new Exception("Suppression de fichier impossible");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void CreationCelluleText(String sheet, String contenu, int ligne, int colonne)
	{
		XSSFSheet feuille =  wb.getSheet(sheet);
		Row row = feuille.getRow(ligne);
		if (row == null) {
			row = feuille.createRow(ligne);
		}
		Cell cell = null;
		cell = row.getCell((short) colonne);
		cell.setCellValue(contenu);
	}
	
	public static void CreationCelluleValeur(String sheet, Object contenu, int ligne, int colonne)
	{
		XSSFSheet feuille =  wb.getSheet(sheet);
		Row row = feuille.getRow(ligne);
		if (row == null) {
			row = feuille.createRow(ligne);
		}
		Cell cell = null;
		cell = row.getCell((short) colonne);
		if(cell == null)
			cell = row.createCell((short)colonne);
		if(contenu instanceof Integer)
		{
			cell.setCellValue((Integer)contenu);
		}
		else if(contenu instanceof Double)
		{	
			cell.setCellValue((Double)contenu);
		}
		else if(contenu instanceof String)
		{
			cell.setCellValue((String)contenu);
		}
		else
			cell.setCellValue(contenu.toString());
	}
	
	public static void CreationCelluleFormule(String sheet, String contenu, int ligne, int colonne)
	{
		XSSFSheet feuille =  wb.getSheet(sheet);
		Row row = feuille.getRow(ligne);
		if (row == null) {
			row = feuille.createRow(ligne);
		}
		Cell cell = null;
		cell = row.getCell((short) colonne);
		cell.setCellFormula(contenu);
	}
	
	public static void ViderCellule(String sheet, int ligne, int colonne)
	{
		XSSFSheet feuille =  wb.getSheet(sheet);
		Row row = feuille.getRow(ligne);
		if (row == null) {
			row = feuille.createRow(ligne);
		}
		Cell cell = null;
		cell = row.getCell((short) colonne);
		cell.setCellValue("");
	}
	
	public static void ColorierLigne(String sheet, int ligne, IndexedColors couleur, XSSFWorkbook wb)
	{
		XSSFSheet feuille =  wb.getSheet(sheet);
		Row row = feuille.getRow(ligne);
		if (row == null) {
			row = feuille.createRow(ligne);
		}
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillBackgroundColor(couleur.getIndex());
		row.setRowStyle(cellStyle);
	}
	
	public static String GetLastModificationDate(String filepath)
	{
		//System.out.println("Path : " + filepath);
		String res = "1000/10/10";
		//System.out.println(filepath);
		File file = new File(filepath);
		//System.out.println(file.toString());
		try
		{
			XSSFWorkbook wbf = (XSSFWorkbook) WorkbookFactory.create(file);
			
			//System.out.println(rds.get(0).toString());
			res = GetCelluleValeur("General Information",4,1, wbf);
		}
		 catch (EncryptedDocumentException e) {
				System.out.println(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				System.out.println(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return res;
		
	}
	
	private static String GetCelluleValeur(String sheet ,int ligne ,int colonne, XSSFWorkbook wbf)
	{
		String res = "";
		//System.out.println("Sheet : " + sheet);
		XSSFSheet feuille =  wbf.getSheet(sheet);
		Row row = feuille.getRow(ligne);
		if (row == null) {
			row = feuille.createRow(ligne);
		}
		Cell cell = null;
		cell = row.getCell((short) colonne);
		res = cell.getStringCellValue();
		
		return res;
	}

	
	private static int GetCelluleValeurInt(String sheet ,int ligne ,int colonne, XSSFWorkbook wbf)
	{
		int res = 0;
		//System.out.println("Sheet : " + sheet);
		XSSFSheet feuille =  wbf.getSheet(sheet);
		Row row = feuille.getRow(ligne);
		if (row == null) {
			row = feuille.createRow(ligne);
		}
		Cell cell = null;
		cell = row.getCell((short) colonne);
		res = (int)cell.getNumericCellValue();
		
		return res;
	}

	
	private static double GetCelluleValeurDouble(String sheet ,int ligne ,int colonne, XSSFWorkbook wbf)
	{
		double res = 0;
		//System.out.println("Sheet : " + sheet);
		XSSFSheet feuille =  wbf.getSheet(sheet);
		Row row = feuille.getRow(ligne);
		if (row == null) {
			row = feuille.createRow(ligne);
		}
		Cell cell = null;
		cell = row.getCell((short) colonne);
		res = cell.getNumericCellValue();
		
		return res;
	}
	
	public static void ColorierColonne(XSSFSheet sheet, int colonne, IndexedColors couleur, XSSFWorkbook wb)
	{
		Row row = null;
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillBackgroundColor(couleur.getIndex());
		for (Iterator rowIt = sheet.rowIterator(); rowIt.hasNext();) 
		{
			row = (Row) rowIt.next();
			row.getCell(0).setCellStyle(cellStyle);
		}
	}
	public static void ModifierPolice(XSSFSheet sheet, int ligne, int colonne, String police, int taille, IndexedColors couleur, XSSFWorkbook wb)
	{
		XSSFFont fonte = wb.createFont();
		fonte.setFontHeightInPoints((short) taille);
		fonte.setFontName(police);
		fonte.setColor(couleur.getIndex());
	}
	public static void RemplirLigneText(String sheet, int ligne, int debutColonne, ArrayList<String> data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleText(sheet, data.get(i), ligne, i+debutColonne);
		}
	}
	public static void RemplirColonneText(String sheet, int colonne, int debutLigne, ArrayList<String>data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleText(sheet, data.get(i), i+debutLigne, colonne);
		}
	}
	public static void RemplirLigneValeurs(String sheet, int ligne, int debutColonne, ArrayList<Integer> data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleValeur(sheet, data.get(i), ligne, i+debutColonne);
		}
	}
	public static void RemplirColonneValeursInt(String sheet, int colonne, int debutLigne, ArrayList<Integer>data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleValeur(sheet, data.get(i), i+debutLigne, colonne);
		}
	}
	public static void RemplirColonneValeursDouble(String sheet, int colonne, int debutLigne, ArrayList<Double>data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleValeur(sheet, data.get(i), i+debutLigne, colonne);
		}
	}
	
	public static void SupprimerLigne(String sheet, int ligne)
	{
		XSSFSheet feuille =  wb.getSheet(sheet);
		Row row = feuille.getRow(ligne);
		Cell cell = null;
		for (Iterator cellIt = row.cellIterator(); cellIt.hasNext();) 
		{
			cell = (Cell) cellIt.next();
			cell.setCellValue("");
		}
	}
	public static void SupprimerColonne(String sheet, int colonne)
	{
		XSSFSheet feuille =  wb.getSheet(sheet);
		Row row = null;
		for (Iterator rowIt = feuille.rowIterator(); rowIt.hasNext();) 
		{
			row = (Row) rowIt.next();
			row.getCell(0).setCellValue("");
		}
	}
	
	public static void DuplicateTemplate(String out)
	{
		File file = new File("files/template.xlsx");
		try {
			System.out.println("allo1");
			XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(file);
			System.out.println("allo2");
			CreationFichier("files/testtest.xlsx");
			System.out.println("allo3");
		} catch (EncryptedDocumentException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void RemplirColonnePhaseInt(String sheet,HashMap<String, Integer>hmap, Phase phase, int dinucleotide)
	{
		//parcourir tout le hashmap et ajouter dans la colonne 1+Phase à la bonne ligne le nombre adéquat
		ArrayList<String> trinucleotide = Utils.getListOfTriNucleotideCAPSLOCK();
		ArrayList<Integer> list = new ArrayList<Integer>();
		/*for(String s : trinucleotide)
		{
			list.add(hmap.get(s.toLowerCase()));
		}*/
		if(dinucleotide==0)
		{
			for(String s : trinucleotide)
			{
				list.add(hmap.get(s.toLowerCase()));
			}
		}
		else
		{
			for(String s : Utils.getListOfDiNucleotideCAPSLOCK())
			{
				list.add(hmap.get(s.toLowerCase()));
			}
		}
		if(phase == Phase.PrefPhase0 || phase == Phase.PrefPhase1)
			RemplirColonneValeursInt(sheet, phase.ordinal()+2+dinucleotide*10, 1, list);
		else
			RemplirColonneValeursInt(sheet, phase.ordinal()+2+dinucleotide*12, 1, list);
	}
	
	private static void RemplirColonnePhaseDouble(String sheet,HashMap<String, Double>hmap, Phase phase, int dinucleotide)
	{
		//parcourir tout le hashmap et ajouter dans la colonne 1+Phase à la bonne ligne le nombre adéquat
		ArrayList<String> trinucleotide = Utils.getListOfTriNucleotideCAPSLOCK();
		ArrayList<Double> list = new ArrayList<Double>();
		if(dinucleotide==0)
		{
			for(String s : trinucleotide)
			{
				list.add(hmap.get(s.toLowerCase()));
			}
		}
		else
		{
			for(String s : Utils.getListOfDiNucleotideCAPSLOCK())
			{
				list.add(hmap.get(s.toLowerCase()));
			}
		}
		if(phase == Phase.PrefPhase0 || phase == Phase.PrefPhase1)
			RemplirColonneValeursDouble(sheet, phase.ordinal()+2+dinucleotide*10, 1, list);
		else
			RemplirColonneValeursDouble(sheet, phase.ordinal()+2+dinucleotide*12, 1, list);
	}
	
	private static void MajFormules()
	{
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluateAll();
	}
	
	public static void testGeneration()
	{
		String fichierTest = "files/chromosome_NC_015850.1.txt";
		ResultData rd = new ResultData();
		try {
			rd = GestionFichier.readWithFileName(fichierTest);
			ArrayList<ResultData> rds = new ArrayList<ResultData>();
			rds.add(rd);
			System.out.println(rd.getTrinucleotide().getFreqHMAP1());
			CreateExcel("test/test.xlsx", rds);
			System.out.println(rd.getDinucleotide().getPrefHMAP(0));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void RemplirInfo(ResultData rd)
	{
		wb.getSheetAt(0);
		//System.out.println(rd.getOrganismName());
		CreationCelluleValeur("General Information", rd.getOrganismName(), 2, 1);
		CreationCelluleValeur("General Information", rd.getLastModifDate(), 4, 1); //last modification
		CreationCelluleValeur("General Information", rd.getNumberCdsSeq(), 6, 1);
		CreationCelluleValeur("General Information", rd.getNumberCdsSeqInvalid() + rd.getCDSInvalide(), 8, 1);
		CreationCelluleValeur("General Information", rd.getNbOrganism(), 10, 1);  //nb organisme
		CreationCelluleValeur("General Information", rd.getNbChromosome(), 3, 4); // chromosome
		CreationCelluleValeur("General Information", rd.getNbPlasmid(), 4, 4); //nb plasmid
		CreationCelluleValeur("General Information", rd.getNbDna(), 5, 4); //dna
		CreationCelluleValeur("General Information", rd.getNbDna(), 5, 4); //dna
	}
	

	public static HashMap<String, Double> GetColonneValeursDouble(String sheet, int colonne, int debutLigne, XSSFWorkbook wbf)
	{
		HashMap<String, Double> res = new HashMap<String, Double>();
		ArrayList<String> tri = Utils.getListOfTriNucleotide();
		for(int i =0; i < tri.size(); i++)
		{
			res.put(tri.get(i),GetCelluleValeurDouble(sheet, i+debutLigne, colonne, wbf));
		}
		return res;
	}
	

	public static HashMap<String, Integer> GetColonneValeursInt(String sheet, int colonne, int debutLigne, XSSFWorkbook wbf)
	{
		HashMap<String, Integer> res = new HashMap<String, Integer>();
		ArrayList<String> tri = Utils.getListOfTriNucleotide();
		for(int i =0; i < tri.size(); i++)
		{
			res.put(tri.get(i),GetCelluleValeurInt(sheet, i+debutLigne, colonne, wbf));
		}
		return res;
	}
	
	private static HashMap<String,Double> GetColonnePhaseDouble(String sheet, Phase phase, int dinucleotide,XSSFWorkbook wbf)
	{
		if(phase == Phase.PrefPhase0 || phase == Phase.PrefPhase1)
			return GetColonneValeursDouble(sheet, phase.ordinal()+2+dinucleotide*10, 1, wbf);
		else
			return GetColonneValeursDouble(sheet, phase.ordinal()+2+dinucleotide*12, 1, wbf);
	}
	
	private static HashMap<String,Integer> GetColonnePhaseInt(String sheet, Phase phase, int dinucleotide,XSSFWorkbook wbf)
	{
		if(phase == Phase.PrefPhase0 || phase == Phase.PrefPhase1)
			return GetColonneValeursInt(sheet, phase.ordinal()+2+dinucleotide*10, 1, wbf);
		else
			return GetColonneValeursInt(sheet, phase.ordinal()+2+dinucleotide*12, 1, wbf);
	}
	
	public static ArrayList<ResultData> GetFromExcel(String filepath)
	{
		ArrayList<ResultData>res = new ArrayList<ResultData>();
		
				File file = new File(filepath);
				//System.out.println(file.toString());
				try
				{
					XSSFWorkbook wbf = (XSSFWorkbook) WorkbookFactory.create(file);
					XSSFSheet sheet;
					ResultData temp;
					String onglet = "";
					int getNumberCdsSeqInvalid = 0, getCDSInvalide = 0;
					Trinucleotide t;
					Dinucleotide d;
					for(int i = wbf.getNumberOfSheets(); i>=0 ; i--)
					{
						temp = new ResultData();
						sheet = wbf.getSheetAt(i);
						onglet = sheet.getSheetName();
						t = null;
						d = null;
						if(onglet.equals("General Information"))
						{
							temp.setOrganismName(GetCelluleValeur("General Information", 2, 1, wbf));
							temp.setLastModifDate(GetCelluleValeur("General Information", 4, 1, wbf));
							temp.setNumberCdsSeq(GetCelluleValeurInt("General Information", 6, 1, wbf));
							
							temp.setNumberCdsSeqInvalid(GetCelluleValeurInt("General Information", 8, 1, wbf));
							temp.setNbOrganism(GetCelluleValeurInt("General Information", 10, 1, wbf));
							temp.setNbChromosome(GetCelluleValeurInt("General Information",3, 4, wbf));
							temp.setNbPlasmid(GetCelluleValeurInt("General Information",4, 4, wbf));
							temp.setNbDna(GetCelluleValeurInt("General Information",5, 4, wbf));
							temp.setCDSInvalide(getCDSInvalide);
							temp.setNumberCdsSeqInvalid(getNumberCdsSeqInvalid);
						}
						else
						{
							t  = new Trinucleotide();
							d = new Dinucleotide();
							
							t.setFreqHMAP0(GetColonnePhaseDouble(onglet, Phase.FreqPhase0, 0, wbf));
							t.setFreqHMAP1(GetColonnePhaseDouble(onglet, Phase.FreqPhase1, 0, wbf));
							t.setFreqHMAP2(GetColonnePhaseDouble(onglet, Phase.FreqPhase2, 0, wbf));
							t.setHMAP0(GetColonnePhaseInt(onglet, Phase.Phase0, 0, wbf));
							t.setHMAP1(GetColonnePhaseInt(onglet, Phase.Phase1, 0, wbf));
							t.setHMAP2(GetColonnePhaseInt(onglet, Phase.Phase2, 0, wbf));
							t.setPrefHMAP0(GetColonnePhaseInt(onglet, Phase.PrefPhase0, 0, wbf));
							t.setPrefHMAP1(GetColonnePhaseInt(onglet, Phase.PrefPhase1, 0, wbf));
							t.setPrefHMAP2(GetColonnePhaseInt(onglet, Phase.PrefPhase2, 0, wbf));

							d.setFreqHMAP0(GetColonnePhaseDouble(onglet, Phase.FreqPhase0, 1, wbf));
							d.setFreqHMAP1(GetColonnePhaseDouble(onglet, Phase.FreqPhase1, 1, wbf));
							d.setHMAP0(GetColonnePhaseInt(onglet, Phase.Phase0, 1, wbf));
							d.setHMAP1(GetColonnePhaseInt(onglet, Phase.Phase1, 1, wbf));
							d.setPrefHMAP0(GetColonnePhaseInt(onglet, Phase.PrefPhase0, 1, wbf));
							d.setPrefHMAP1(GetColonnePhaseInt(onglet, Phase.PrefPhase1, 1, wbf));
							
							getCDSInvalide = GetCelluleValeurInt(onglet, 25, 13, wbf);
							getNumberCdsSeqInvalid = GetCelluleValeurInt(onglet, 24, 13, wbf);
							
							temp.setNumberCdsSeq(GetCelluleValeurInt(onglet, 23, 13, wbf));
							temp.setNumberCdsSeqInvalid(getNumberCdsSeqInvalid);
							temp.setCDSInvalide(getCDSInvalide);
							
							temp.setTrinucleotide(t);
							temp.setDinucleotid(d);
							
							
						}
						res.add(temp);
					}
					
				}
				 catch (EncryptedDocumentException e) 
				{
						System.out.println(e.getMessage());
						// TODO Auto-generated catch block
						e.printStackTrace();
				} catch (InvalidFormatException e) 
				{
					System.out.println(e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) 
				{
					System.out.println(e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return res;
	}
	
	public static void AjouterOnglet(ResultData rd)
	{
		String onglet = rd.getName();
		wb.cloneSheet(1,onglet);
		RemplirColonnePhaseDouble(onglet, rd.getTrinucleotide().getFreqHMAP(0), Phase.FreqPhase0, 0);
		RemplirColonnePhaseDouble(onglet, rd.getTrinucleotide().getFreqHMAP(1), Phase.FreqPhase1, 0);
		RemplirColonnePhaseDouble(onglet, rd.getTrinucleotide().getFreqHMAP(2), Phase.FreqPhase2, 0);
		RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getHMAP(0), Phase.Phase0, 0);
		RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getHMAP(1), Phase.Phase1, 0);
		RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getHMAP(2), Phase.Phase2, 0);
		RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getPrefHMAP(0), Phase.PrefPhase0, 0);
		RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getPrefHMAP(1), Phase.PrefPhase1, 0);
		RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getPrefHMAP(2), Phase.PrefPhase2, 0);

		RemplirColonnePhaseDouble(onglet, rd.getDinucleotide().getFreqHMAP(0), Phase.FreqPhase0, 1);
		RemplirColonnePhaseDouble(onglet, rd.getDinucleotide().getFreqHMAP(1), Phase.FreqPhase1, 1);
		RemplirColonnePhaseInt(onglet, rd.getDinucleotide().getHMAP(0), Phase.Phase0, 1);
		RemplirColonnePhaseInt(onglet, rd.getDinucleotide().getHMAP(1), Phase.Phase1, 1);
		RemplirColonnePhaseInt(onglet, rd.getDinucleotide().getPrefHMAP(0), Phase.PrefPhase0, 1);
		RemplirColonnePhaseInt(onglet, rd.getDinucleotide().getPrefHMAP(1), Phase.PrefPhase1, 1);
		
		CreationCelluleValeur(onglet, rd.getNumberCdsSeq(), 23, 13);
		CreationCelluleValeur(onglet, rd.getNumberCdsSeqInvalid(), 24, 13);
		CreationCelluleValeur(onglet, rd.getCDSRestants(), 25, 13);
		CreationCelluleValeur(onglet, rd.getCDSInvalide(), 26, 13);
		CreationCelluleValeur(onglet, rd.getCDSTraites(), 27, 13);
	}
	public static void CreateExcel(String chemin,  ArrayList<ResultData>rds)
	{

		try
		{
			File file = new File("files/templateEdit.xlsx");
			wb = (XSSFWorkbook) WorkbookFactory.create(file);
			//System.out.println(rds.get(0).toString());
			int CDSNonValides = 0;
			for(int i = 0; i< rds.size() ; i++)
			{
				if(i==0)
				{
					RemplirInfo(rds.get(i));
				}
				else
				{
					AjouterOnglet(rds.get(i));
					if(rds.get(i).getName().contains("Sum"))
						CDSNonValides = CDSNonValides + rds.get(i).getCDSInvalide() + rds.get(i).getNumberCdsSeqInvalid();
				}
			}
			CreationCelluleValeur("General Information", CDSNonValides, 8, 1);
			wb.removeSheetAt(1);
			MajFormules();
			CreationFichier(chemin);
		}
		catch (EncryptedDocumentException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public static void CreateFromTemplate(String out, ResultData rd)
	{
		File file = new File("files/templateEdit.xlsx");
		try {
			wb = (XSSFWorkbook) WorkbookFactory.create(file);
			ArrayList<String> onglets = new ArrayList<String>();
			ArrayList<String> remove = new ArrayList<String>();
			if(rd.isChloroplast())
			{
				onglets.add("Sum_Chloroplast");
			}
			else
				remove.add("Sum_Chloroplast");
				
			if(rd.isChromosome())
			{
				onglets.add("Sum_Chromosome");
			}
			else
				remove.add("Sum_Chromosome");
			
			if(rd.isDna())
			{
				onglets.add("Sum_DNA");
			}
			else
				remove.add("Sum_DNA");
			
			if(rd.isLinkage())
			{
				onglets.add("Sum_Linkage");
			}
			else 
				remove.add("Sum_Linkage");
			
			if(rd.isMitochondrion())
			{
				onglets.add("Sum_Mitochondrion");
			}
			else
				remove.add("Sum_Mitochondrion");
			
			for(String onglet : onglets)
			{
				RemplirColonnePhaseDouble(onglet, rd.getTrinucleotide().getFreqHMAP(0), Phase.FreqPhase0, 0);
				RemplirColonnePhaseDouble(onglet, rd.getTrinucleotide().getFreqHMAP(1), Phase.FreqPhase1, 0);
				RemplirColonnePhaseDouble(onglet, rd.getTrinucleotide().getFreqHMAP(2), Phase.FreqPhase2, 0);
				RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getHMAP(0), Phase.Phase0, 0);
				RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getHMAP(1), Phase.Phase1, 0);
				RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getHMAP(2), Phase.Phase2, 0);
				RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getPrefHMAP(0), Phase.PrefPhase0, 0);
				RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getPrefHMAP(1), Phase.PrefPhase1, 0);
				RemplirColonnePhaseInt(onglet, rd.getTrinucleotide().getPrefHMAP(2), Phase.PrefPhase2, 0);

				RemplirColonnePhaseDouble(onglet, rd.getDinucleotide().getFreqHMAP(0), Phase.FreqPhase0, 1);
				RemplirColonnePhaseDouble(onglet, rd.getDinucleotide().getFreqHMAP(1), Phase.FreqPhase1, 1);
				RemplirColonnePhaseInt(onglet, rd.getDinucleotide().getHMAP(0), Phase.Phase0, 1);
				RemplirColonnePhaseInt(onglet, rd.getDinucleotide().getHMAP(1), Phase.Phase1, 1);
				RemplirColonnePhaseInt(onglet, rd.getDinucleotide().getPrefHMAP(0), Phase.PrefPhase0, 1);
				RemplirColonnePhaseInt(onglet, rd.getDinucleotide().getPrefHMAP(1), Phase.PrefPhase1, 1);
			}
			for(String rem : remove)
			{
				wb.removeSheetAt(wb.getSheetIndex(rem));
			}
			RemplirInfo(rd);
			MajFormules();
			CreationFichier(out);
		} catch (EncryptedDocumentException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	/*public static void main(String[] args) 
	{
		testGeneration();
		/*XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("mafeuille");
		ArrayList<String> trinucleotides = Utils.getListOfTriNucleotideCAPSLOCK();
		RemplirColonneText(sheet, 0, 1, trinucleotides);
		ArrayList<Integer> testNombres = new ArrayList<Integer>();
		for(int i=0; i<64; i++) {
			int temp = i*i;
			testNombres.add(temp);
		}
		RemplirColonneValeurs(sheet, 1, 1, testNombres);
		CreationCelluleText(sheet, "Total", 65, 0);
		CreationCelluleFormule(sheet, "SUM(B2:B65)", 65, 1);
		CreationFichier("bwaaa.xlsx", wb);*/
		/* pour �tre s�r que les r�sultats des formules soient update en temps r�el
		 * 
		 * FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluateAll();
	}*/
}
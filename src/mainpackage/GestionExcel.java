package mainpackage;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	
	//Crée un fichier excel vide
	public static void CreationFichierVide(String file)
	{
		XSSFWorkbook wb = new XSSFWorkbook();
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
	
	public static void CreationFichier(String file, XSSFWorkbook wb)
	{
		FileOutputStream fileOut;
		try 
		{
			fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
			System.out.println("fincréationfichier");
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
				System.out.println(file.getName() + " is deleted!");
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
	
	public static void CreationCelluleText(XSSFSheet sheet, String contenu, int ligne, int colonne)
	{
		Row row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		Cell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellValue(contenu);
	}
	
	public static void CreationCelluleValeur(XSSFSheet sheet, Object contenu, int ligne, int colonne)
	{
		Row row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		Cell cell = null;
		cell = row.createCell((short) colonne);
		if(contenu instanceof Integer)
			cell.setCellValue((Integer)contenu);
		else if(contenu instanceof Double)
			cell.setCellValue((Double)contenu);
	}
	
	public static void CreationCelluleFormule(XSSFSheet sheet, String contenu, int ligne, int colonne)
	{
		Row row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		Cell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellFormula(contenu);
	}
	
	public static void ViderCellule(XSSFSheet sheet, int ligne, int colonne)
	{
		Row row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		Cell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellValue("");
	}
	
	public static void ColorierLigne(XSSFSheet sheet, int ligne, IndexedColors couleur, XSSFWorkbook wb)
	{
		Row row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillBackgroundColor(couleur.getIndex());
		row.setRowStyle(cellStyle);
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
	public static void RemplirLigneText(XSSFSheet sheet, int ligne, int debutColonne, ArrayList<String> data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleText(sheet, data.get(i), ligne, i+debutColonne);
		}
	}
	public static void RemplirColonneText(XSSFSheet sheet, int colonne, int debutLigne, ArrayList<String>data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleText(sheet, data.get(i), i+debutLigne, colonne);
		}
	}
	public static void RemplirLigneValeurs(XSSFSheet sheet, int ligne, int debutColonne, ArrayList<Integer> data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleValeur(sheet, data.get(i), ligne, i+debutColonne);
		}
	}
	public static void RemplirColonneValeursInt(XSSFSheet sheet, int colonne, int debutLigne, ArrayList<Integer>data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleValeur(sheet, data.get(i), i+debutLigne, colonne);
		}
	}
	public static void RemplirColonneValeursDouble(XSSFSheet sheet, int colonne, int debutLigne, ArrayList<Double>data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleValeur(sheet, data.get(i), i+debutLigne, colonne);
		}
	}
	
	public static void SupprimerLigne(XSSFSheet sheet, int ligne)
	{
		Row row = sheet.getRow(ligne);
		Cell cell = null;
		for (Iterator cellIt = row.cellIterator(); cellIt.hasNext();) 
		{
			cell = (Cell) cellIt.next();
			cell.setCellValue("");
		}
	}
	public static void SupprimerColonne(XSSFSheet sheet, int colonne)
	{
		Row row = null;
		for (Iterator rowIt = sheet.rowIterator(); rowIt.hasNext();) 
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
			CreationFichier("files/testtest.xlsx", wb);
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
	
	private static void RemplirColonnePhaseInt(XSSFSheet sheet,HashMap<String, Integer>hmap, Phase phase)
	{
		//parcourir tout le hashmap et ajouter dans la colonne 1+Phase à la bonne ligne le nombre adéquat
		ArrayList<String> trinucleotide = Utils.getListOfTriNucleotideCAPSLOCK();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(String s : trinucleotide)
		{
			list.add(hmap.get(s));
		}
		RemplirColonneValeursInt(sheet, phase.ordinal()+1, 0, list);
	}
	
	private static void RemplirColonnePhaseDouble(XSSFSheet sheet,HashMap<String, Double>hmap, Phase phase)
	{
		//parcourir tout le hashmap et ajouter dans la colonne 1+Phase à la bonne ligne le nombre adéquat
		ArrayList<String> trinucleotide = Utils.getListOfTriNucleotideCAPSLOCK();
		ArrayList<Double> list = new ArrayList<Double>();
		for(String s : trinucleotide)
		{
			list.add(hmap.get(s));
		}
		RemplirColonneValeursDouble(sheet, phase.ordinal()+1, 0, list);
	}
	
	
	public static void CreateFromTemplate(String out, Trinucleotide tri)
	{
		File file = new File("files/template.xlsx");
		try {
			XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(file);
			XSSFSheet sum_chromosome = wb.getSheetAt(1);
			RemplirColonnePhaseDouble(sum_chromosome, tri.getFreqHMAP(0), Phase.FreqPhase0);
			RemplirColonnePhaseDouble(sum_chromosome, tri.getFreqHMAP(1), Phase.FreqPhase1);
			RemplirColonnePhaseDouble(sum_chromosome, tri.getFreqHMAP(2), Phase.FreqPhase2);
			RemplirColonnePhaseInt(sum_chromosome, tri.getHMAP(0), Phase.Phase0);
			RemplirColonnePhaseInt(sum_chromosome, tri.getHMAP(1), Phase.Phase1);
			RemplirColonnePhaseInt(sum_chromosome, tri.getHMAP(2), Phase.Phase2);
			RemplirColonnePhaseInt(sum_chromosome, tri.getPrefHMAP(0), Phase.PrefPhase0);
			RemplirColonnePhaseInt(sum_chromosome, tri.getPrefHMAP(1), Phase.PrefPhase1);
			RemplirColonnePhaseInt(sum_chromosome, tri.getPrefHMAP(2), Phase.PrefPhase2);
			CreationFichier("files/testtest.xlsx", wb);
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
	
	public static void main(String[] args) 
	{
		System.out.println("allo");
		DuplicateTemplate("testdupli");
		System.out.println("à l'huile");
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
		evaluator.evaluateAll();*/
	}
}
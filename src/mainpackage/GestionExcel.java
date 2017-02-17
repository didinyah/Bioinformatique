package mainpackage;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/*import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;*/
import org.apache.poi.xssf.*;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GestionExcel 
{
	// TYPES DE CELL :HSSFCell.[CELL_TYPE_FORMULA, CELL_TYPE_NUMERIC, ou CELL_TYPE_STRING]
	
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
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
		
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
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
		
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
	
	public static void CreationCelluleValeur(XSSFSheet sheet, int contenu, int ligne, int colonne)
	{
		Row row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		Cell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellValue(contenu);
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
	public static void RemplirColonneValeurs(XSSFSheet sheet, int colonne, int debutLigne, ArrayList<Integer>data)
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
	public static void main(String[] args) 
	{
		XSSFWorkbook wb = new XSSFWorkbook();
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
		CreationFichier("bwaaa.xlsx", wb);
		/* pour �tre s�r que les r�sultats des formules soient update en temps r�el
		 * 
		 * FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluateAll();*/
	}
}
package mainpackage;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

public class GestionExcel 
{
	// TYPES DE CELL :HSSFCell.[CELL_TYPE_FORMULA, CELL_TYPE_NUMERIC, ou CELL_TYPE_STRING]
	
	//Cr√©e un fichier excel vide
	public static void CreationFichierVide(String file)
	{
		HSSFWorkbook wb = new HSSFWorkbook();
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
	
	public static void CreationFichier(String file, HSSFWorkbook wb)
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
	
	public static void CreationCelluleText(HSSFSheet sheet, String contenu, int ligne, int colonne)
	{
		HSSFRow row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		HSSFCell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellValue(contenu);
	}
	
	public static void CreationCelluleValeur(HSSFSheet sheet, int contenu, int ligne, int colonne)
	{
		HSSFRow row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		HSSFCell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellValue(contenu);
	}
	
	public static void CreationCelluleFormule(HSSFSheet sheet, String contenu, int ligne, int colonne)
	{
		HSSFRow row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		HSSFCell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellFormula(contenu);
	}
	
	public static void ViderCellule(HSSFSheet sheet, int ligne, int colonne)
	{
		HSSFRow row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		HSSFCell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellValue("");
	}
	
	public static void ColorierLigne(HSSFSheet sheet, int ligne, HSSFColor couleur, HSSFWorkbook wb)
	{
		HSSFRow row = sheet.getRow(ligne);
		if (row == null) {
			row = sheet.createRow(ligne);
		}
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillBackgroundColor(couleur.getIndex());
		row.setRowStyle(cellStyle);
	}
	
	public static void ColorierColonne(HSSFSheet sheet, int colonne, HSSFColor couleur, HSSFWorkbook wb)
	{
		HSSFRow row = null;
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillBackgroundColor(couleur.getIndex());
		for (Iterator rowIt = sheet.rowIterator(); rowIt.hasNext();) 
		{
			row = (HSSFRow) rowIt.next();
			row.getCell(0).setCellStyle(cellStyle);
		}
	}
	public static void ModifierPolice(HSSFSheet sheet, int ligne, int colonne, String police, int taille, HSSFColor couleur, HSSFWorkbook wb)
	{
		HSSFFont fonte = wb.createFont();
		fonte.setFontHeightInPoints((short) taille);
		fonte.setFontName(police);
		fonte.setColor(couleur.getIndex());
	}
	public static void RemplirLigneText(HSSFSheet sheet, int ligne, int debutColonne, ArrayList<String> data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleText(sheet, data.get(i), ligne, i+debutColonne);
		}
	}
	public static void RemplirColonneText(HSSFSheet sheet, int colonne, int debutLigne, ArrayList<String>data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleText(sheet, data.get(i), i+debutLigne, colonne);
		}
	}
	public static void RemplirLigneValeurs(HSSFSheet sheet, int ligne, int debutColonne, ArrayList<Integer> data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleValeur(sheet, data.get(i), ligne, i+debutColonne);
		}
	}
	public static void RemplirColonneValeurs(HSSFSheet sheet, int colonne, int debutLigne, ArrayList<Integer>data)
	{
		for(int i =0; i < data.size(); i++)
		{
			CreationCelluleValeur(sheet, data.get(i), i+debutLigne, colonne);
		}
	}
	public static void SupprimerLigne(HSSFSheet sheet, int ligne)
	{
		HSSFRow row = sheet.getRow(ligne);
		HSSFCell cell = null;
		for (Iterator cellIt = row.cellIterator(); cellIt.hasNext();) 
		{
			cell = (HSSFCell) cellIt.next();
			cell.setCellValue("");
		}
	}
	public static void SupprimerColonne(HSSFSheet sheet, int colonne)
	{
		HSSFRow row = null;
		for (Iterator rowIt = sheet.rowIterator(); rowIt.hasNext();) 
		{
			row = (HSSFRow) rowIt.next();
			row.getCell(0).setCellValue("");
		}
	}
	public static void main(String[] args) 
	{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("mafeuille");
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
		CreationFichier("bwaaa.xls", wb);
		/* pour Ítre s˚r que les rÈsultats des formules soient update en temps rÈel
		 * 
		 * FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluateAll();*/
	}
}
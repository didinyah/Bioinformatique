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

public class GestionExcel 
{
	
	//Crée un fichier excel vide
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
	
	public static void CreationCellule(HSSFSheet sheet, String contenu, int ligne, int colonne)
	{
		HSSFRow row = sheet.createRow(ligne);
		HSSFCell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellValue(contenu);
	}
	
	public static void ViderCellule(HSSFSheet sheet, int ligne, int colonne)
	{
		HSSFRow row = sheet.createRow(ligne);
		HSSFCell cell = null;
		cell = row.createCell((short) colonne);
		cell.setCellValue("");
	}
	
	public static void ColorierLigne(HSSFSheet sheet, int ligne, HSSFColor couleur, HSSFWorkbook wb)
	{
		HSSFRow row = sheet.createRow(ligne);
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
	public static void RemplirLigne(HSSFSheet sheet, int ligne, int debutColonne, ArrayList<String> data)
	{
		for(int i =debutColonne; i < data.size(); i++)
		{
			CreationCellule(sheet, data.get(i), ligne, i);
		}
	}
	public static void RemplirColonne(HSSFSheet sheet, int colonne, int debutLigne, ArrayList<String>data)
	{
		for(int i =debutLigne; i < data.size(); i++)
		{
			CreationCellule(sheet, data.get(i), i, colonne);
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
		HSSFSheet sheet = wb.createSheet("ma feuille");
		ArrayList<String> trinucleotides = Utils.getListOfTriNucleotideCAPSLOCK();
		RemplirColonne(sheet, 0, 1, trinucleotides);

		CreationFichier("bwaaa.xlsx", wb);
	}
}
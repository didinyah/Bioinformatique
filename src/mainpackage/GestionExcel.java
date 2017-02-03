package mainpackage;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.eval.NotImplementedException;

public class GestionExcel 
{
	public static void CreationFichier(String file)
	{
		throw new NotImplementedException("");
	}
	public static void SupprimerFichier(String file)
	{
		throw new NotImplementedException("");
	}
	public static void CreationCellule(String file, String contenu, int ligne, int colonne)
	{
		throw new NotImplementedException("");
	}
	public static void ViderCellule(String file, int ligne, int colonne)
	{
		throw new NotImplementedException("");
	}
	public static void ColorierLigne(String file, int ligne, Color couleur)
	{
		throw new NotImplementedException("");
	}
	public static void ColorierColonne(String file, int colonne, Color couleur)
	{
		throw new NotImplementedException("");
	}
	public static void ModifierPolice(String file, int ligne, int colonne, String police)
	{
		throw new NotImplementedException("");
	}
	public static void ModifierTailePolice(String file, int ligne, int colonne, int taille)
	{
		throw new NotImplementedException("");
	}
	public static void ModifierCouleurPolice(String file, int ligne, int colonne, Color couleur)
	{
		throw new NotImplementedException("");
	}
	public static void RemplirLigne(String file, int ligne, String[]data)
	{
		throw new NotImplementedException("");
	}
	public static void RemplirColonne(String file, int colonne, String[]data)
	{
		throw new NotImplementedException("");
	}
	public static void SupprimerLigne(String file, int ligne)
	{
		throw new NotImplementedException("");
	}
	public static void SupprimerColonne(String file, int colonne)
	{
		throw new NotImplementedException("");
	}
	public static void main(String[] args) 
	{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("ma feuille");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell((short)0);
		cell.setCellValue(10);
		row.createCell((short)1).setCellValue(20);
		FileOutputStream fileOut;
		try 
		{
			fileOut = new FileOutputStream("monfichier.xls");
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
}
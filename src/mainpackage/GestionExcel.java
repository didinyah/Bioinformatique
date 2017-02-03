package mainpackage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GestionExcel {
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
package mainpackage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class GestionFichier {
	
	public static void main(String[] args) throws IOException {
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader("files/genomes_euks.csv"));
		    String[] nextLine = reader.readNext();
		    /*while ((nextLine = reader.readNext()) != null) {
		       // nextLine[] is an array of values from the line*/
		    for(int i=0; i<nextLine.length; i++)
		    {
		    	System.out.println(nextLine[i]);
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
}

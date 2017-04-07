package mainpackage.Chargement;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import mainpackage.Organism;


/* 
 * Classe Chargement
 * Permet de créer la barre de chargement
 * L'instance créée à partir de la classe dessine l'animation
 */

public class Chargement extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		Chargement c = new Chargement(1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.send("Virus", 5);
		Organism o = new Organism();
		o.setKingdom("Virus");
		o.setName("Virus1");
		Organism o2 = new Organism();
		o2.setKingdom("Virus");
		o2.setName("Virus2");
		Organism o3 = new Organism();
		o3.setKingdom("Virus");
		o3.setName("Virus3");
		Organism o4 = new Organism();
		o4.setKingdom("Virus");
		o4.setName("Virus4");
		Organism o5 = new Organism();
		o5.setKingdom("Virus");
		o5.setName("Virus5");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.send(o);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.send(o2);
		c.send(o3);
		c.send(o4);
		c.send(o5);
	}
	
	private Frame mainFrame;
	private JTextArea logFrame;
	private GlobalJPanel panel;
	private int dataCount;
	
	private int width = 1100;
	private int height = 700;
	private int panelWidth = 700;
	
	public Chargement(int nombreMax){
		dataCount = nombreMax;
		prepareGUI();
	}

	private void prepareGUI(){
		mainFrame = new Frame("Charging");
		mainFrame.setSize(width, height);
		mainFrame.setLayout(null);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});
		
		JTextArea logs = new JTextArea();
		logs.setEditable(false);
		logs.setBounds(panelWidth, 0, mainFrame.getWidth()-panelWidth, height);
		mainFrame.add(logs);
		logFrame = logs;
		
		final GlobalJPanel gjp = new GlobalJPanel(panelWidth, height, dataCount, logFrame);
		mainFrame.add(gjp);
		panel = gjp;
		
		mainFrame.addComponentListener(new ComponentAdapter() 
		{  
	        public void componentResized(ComponentEvent evt) {
	        	int newPanelWidth = (int)(panelWidth/(double)width*mainFrame.getWidth());
	        	gjp.Resized(newPanelWidth, mainFrame.getHeight());
	        	logFrame.setBounds(newPanelWidth, 0, mainFrame.getWidth()-newPanelWidth, mainFrame.getHeight());
	        }
		});
		mainFrame.setVisible(true);  
	}
	
	//Fonction qui récupère les catégories du chargement
	public void send(Organism s){
		panel.setElement(s);
	}
	
	public void send(String s, int dataCount){
		panel.setElement(s, dataCount);
	}
	
	public void log(String s){
		panel.log(s);
		System.out.println("Un message a été log");
	}
}

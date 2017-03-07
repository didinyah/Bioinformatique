package mainpackage.Chargement;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		@SuppressWarnings("unused")
		Chargement  c = new Chargement();
		c.send("Virus");
		Organism o = new Organism();
		o.setKingdom("Virus");
		o.setName("Virus1");
		Organism o2 = new Organism();
		o2.setKingdom("Virus");
		o2.setName("Virus2");
		c.send(o);
		c.send(o2);
	}
	
	private Frame mainFrame;
	private GlobalJPanel panel;
	
	public Chargement(){
		prepareGUI();
	}

	private void prepareGUI(){
		mainFrame = new Frame("Charging");
		mainFrame.setSize(600,700);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});
		
		final GlobalJPanel gjp = new GlobalJPanel(mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.add(gjp);
		panel = gjp;
		gjp.startCharging();
		
		mainFrame.addComponentListener(new ComponentAdapter() 
		{  
	        public void componentResized(ComponentEvent evt) {
	        	gjp.Resized(mainFrame.getWidth(), mainFrame.getHeight());
	        }
		});
		mainFrame.setVisible(true);  
	}
	
	//Fonction qui récupère les catégories du chargement
	public void send(Organism s){
		panel.setElement(s);
	}
	
	public void send(String s){
		panel.setElement(s);
	}
}

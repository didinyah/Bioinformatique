package mainpackage.Chargement;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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
	
	private Frame mainFrame;
	private JTextArea logFrame;
	public GlobalJPanel panel;
	private int dataCount;
	private JScrollPane sp;
	
	private int width = 1100;
	private int height = 700;
	private int panelWidth = 700;
	private int totalData = 0;
	
	public Chargement(int nombreKingdom, int total){
		dataCount = nombreKingdom;
		totalData = total;
		prepareGUI();
	}

	private void prepareGUI(){
		mainFrame = new Frame("Chargement");
		mainFrame.setSize(width, height);
		mainFrame.setLayout(null);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});
				
		JTextArea logs = new JTextArea();
		logs.setEditable(false);
		//logs.setBounds(panelWidth, 0, mainFrame.getWidth()-panelWidth, height);
		//mainFrame.add(logs);
		logFrame = logs;
		
		JScrollPane scroll = new JScrollPane (logs, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(panelWidth, 0, mainFrame.getWidth()-panelWidth, height);
		sp = scroll;
		mainFrame.add(scroll);
		
		final GlobalJPanel gjp = new GlobalJPanel(panelWidth, height, dataCount, logFrame, scroll, totalData);
		mainFrame.add(gjp);
		panel = gjp;
		
		mainFrame.addComponentListener(new ComponentAdapter() 
		{  
	        public void componentResized(ComponentEvent evt) {
	        	int newPanelWidth = (int)(panelWidth/(double)width*mainFrame.getWidth());
	        	gjp.Resized(newPanelWidth, mainFrame.getHeight());
	        	logFrame.setBounds(newPanelWidth, 0, mainFrame.getWidth()-newPanelWidth, mainFrame.getHeight());
	        	sp.setBounds(newPanelWidth, 0, mainFrame.getWidth()-newPanelWidth, mainFrame.getHeight());
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
	
	public void send(int one){
		panel.setElement(one);
	}
	
	public void log(String s){
		panel.log(s);
	}
	
	public boolean isDone(){
		return panel.finished;
	}
}

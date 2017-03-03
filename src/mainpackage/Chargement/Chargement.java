package mainpackage.Chargement;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.sun.javafx.geom.Ellipse2D;

/* 
 * Classe Chargement
 * Permet de créer la barre de chargement
 * L'instance créée à partir de la classe dessine l'animation
 */

public class Chargement extends Frame{
	public static void main(String[] args){
		Chargement  c = new Chargement();
	}
	
	private Frame mainFrame;
	
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
		gjp.startCharging();
		
		mainFrame.addComponentListener(new ComponentAdapter() 
		{  
	        public void componentResized(ComponentEvent evt) {
	        	gjp.Resized(mainFrame.getWidth(), mainFrame.getHeight());
	        }
		});
		mainFrame.setVisible(true);  
	}
}

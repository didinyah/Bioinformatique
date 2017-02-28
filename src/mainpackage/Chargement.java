package mainpackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import com.sun.javafx.geom.Ellipse2D;

/* 
 * Classe Chargement
 * Permet de créer la barre de chargement
 * L'instance créée à partir de la classe dessine l'animation
 */
public class Chargement extends Frame{
	private Frame mainFrame;
	private Panel controlPanel;
	private MyCanvas mc;

	public Chargement(){
	   prepareGUI();
	}

	public static void main(String[] args){
	   Chargement  c = new Chargement();
	   c.startCharging();
	}

	private void prepareGUI(){
	   mainFrame = new Frame("Charging");
	   mainFrame.setSize(600,600);
	   mainFrame.setLayout(new GridLayout(1, 1));
	   mainFrame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent windowEvent){
	         System.exit(0);
	      }        
	   });
	   
	   MyCanvas c = new MyCanvas(mainFrame.getHeight(),mainFrame.getWidth());
	   mc = c;
	   mainFrame.add(c);
	   mainFrame.setVisible(true);  
	}
	
	private void startCharging(){
		new Thread(new Runnable(){
			public void run(){
				for(int i = 1; i <= 100; i++){
					try {
						mc.updateProgress(i);
						mc.repaint();
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	class MyCanvas extends Canvas {
		private int progressCenter = 0;
		
		public MyCanvas (int lo, int la) {
			setSize(lo, la);
	   }

	   public void paint (Graphics g) {
	      Graphics2D g2;
	      g2 = (Graphics2D) g;
	      g2.setRenderingHints(new RenderingHints(
	    		  RenderingHints.KEY_ANTIALIASING, 
	    		  RenderingHints.VALUE_ANTIALIAS_ON));
	      
	      //Fond
	      GradientPaint gp = new GradientPaint(mainFrame.getWidth()/2, 0, new Color(49, 49, 212), mainFrame.getWidth()/2, mainFrame.getHeight(), new Color(175, 175, 209));
	      gradientBackground(g2, gp);
	      
	      //Cercles
	      //drawFullCircle(g2, 300, 300, 100, new Color(255, 255, 0));
	      //drawFullCircle(g2, 300, 300, 90, new Color(49, 49, 212, 1));
	      
	      g2.translate(this.getWidth()/2, this.getHeight()/2);
	      g2.rotate(Math.toRadians(270));
	      Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
	      
	      arc.setFrameFromCenter(new Point(0, 0), new Point(120, 120));
	      arc.setAngleStart(1);
	      arc.setAngleExtent(-progressCenter*3.6);
	      g2.setColor(Color.YELLOW);
	      g2.draw(arc);
	      g2.fill(arc);
	      
	      drawFullCircle(g2, 0, 0, 220, new Color(49, 49, 212));
	      
	      g2.setColor(Color.YELLOW);
	      g2.rotate(Math.toRadians(90));
	      g2.setFont(new Font("Verdana", Font.PLAIN, 50));
	      FontMetrics fm = g2.getFontMetrics();
	      Rectangle2D r = fm.getStringBounds(progressCenter + "%", g2);
	      int x = (0-(int)r.getWidth())/2;
	      int y = (0-(int)r.getHeight())/2+fm.getAscent();
	      g2.drawString(progressCenter + "%", x, y);
	   }
	      
	   public void drawFullCircle(Graphics2D g, int x, int y, int r, Color c) {
		   g.setPaint(c);
		   x = x-(r/2);
		   y = y-(r/2);
		   g.fillOval(x,y,r,r);
	   }
	   
	   public void drawCircle(Graphics2D g, int x, int y, int r, Color c){
		   g.setPaint(c);
	   }
	   
	   public void gradientBackground(Graphics2D g, GradientPaint gp){
		   g.setPaint(gp);
		   g.fillRect(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
	   }
	   
	   public void updateProgress(int progress){
		   progressCenter = progress;
	   }
	}
}

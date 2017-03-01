package mainpackage;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import com.sun.javafx.geom.Ellipse2D;

/* 
 * Classe Chargement
 * Permet de créer la barre de chargement
 * L'instance créée à partir de la classe dessine l'animation
 */
public class Chargement extends Frame{
	private Frame mainFrame;
	private DrawPanel dp;

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
	   
	   DrawPanel d = new DrawPanel(mainFrame.getHeight(),mainFrame.getWidth());
	   dp = d;
	   mainFrame.add(d);
	   mainFrame.setVisible(true);  
	}
	
	private void startCharging(){
		new Thread(new Runnable(){
			public void run(){
				for(int i = 1; i <= 100; i++){
					try {
						dp.updateProgress(i);
						dp.repaint();
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	class DrawPanel extends JPanel {
		private int progressCenter = 0;
		
		public DrawPanel (int lo, int la) {
			setSize(lo, la);
	   }

	   public void paint (Graphics g) {
	      Graphics2D g2;
	      g2 = (Graphics2D) g.create();
	      g2.setRenderingHints(new RenderingHints(
	    		  RenderingHints.KEY_ANTIALIASING, 
	    		  RenderingHints.VALUE_ANTIALIAS_ON));
	      
	      //Fond
	      GradientPaint gp = new GradientPaint(mainFrame.getWidth()/2, 0, new Color(30, 20, 120), mainFrame.getWidth()/2, mainFrame.getHeight(), new Color(175, 175, 209));
	      gradientBackground(g2, gp);
	      
	      //Cercles
	      //drawFullCircle(g2, 300, 300, 100, new Color(255, 255, 0));
	      //drawFullCircle(g2, 300, 300, 90, new Color(49, 49, 212, 1));
	      
	      createCenterCircle(g2);
	      g2.dispose();
	   }
	      
	   public void drawFullCircle(Graphics2D g, int x, int y, int r) {
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
	   
	   public void createCenterCircle(Graphics2D g){
		   int r = 220;
		   Paint p;
		   g.translate(this.getWidth()/2, this.getHeight()/2);
		   g.rotate(Math.toRadians(270));
		   Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
		      
		   arc.setFrameFromCenter(new Point(0, 0), new Point(115, 115));
		   arc.setAngleStart(1);
		   arc.setAngleExtent(-progressCenter*3.6);
		   g.setColor(new Color(255, 255, 255, 127));
		   g.draw(arc);
		   g.fill(arc);

		   g.translate(-r/2, -r/2);
		   // Couleur du centre
	       g.setColor(Color.BLUE);
	       g.fillOval(0, 0, r, r);
	       
	       // Adds shadows at the top
	       p = new GradientPaint(0, 0, new Color(0.0f, 0.0f, 0.0f, 0.4f),
	               0, getHeight(), new Color(0.0f, 0.0f, 0.0f, 0.0f));
	       g.setPaint(p);
	       g.fillOval(0, 0, r, r);
	       
	       // Adds highlights at the bottom 
	       p = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.0f),
	               0, getHeight(), new Color(1.0f, 1.0f, 1.0f, 0.4f));
	       g.setPaint(p);
	       g.fillOval(0, 0, r, r);
	       
	       // Creates dark edges for 3D effect
	       p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
	               getHeight() / 2.0), getWidth() / 2.0f,
	               new float[] { 0.0f, 1.0f },
	               new Color[] { new Color(6, 76, 160, 127),
	                   new Color(0.0f, 0.0f, 0.0f, 0.8f) });
	       g.setPaint(p);
	       g.fillOval(0, 0, r, r);
	        
	       // Adds oval inner highlight at the bottom
	       p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
	               getHeight() * 1.5), getWidth() / 2.3f,
	               new Point2D.Double(getWidth() / 2.0, getHeight() * 1.75 + 6),
	               new float[] { 0.0f, 0.8f },
	               new Color[] { new Color(64, 142, 203, 255),
	                   new Color(64, 142, 203, 0) },
	               RadialGradientPaint.CycleMethod.NO_CYCLE,
	               RadialGradientPaint.ColorSpaceType.SRGB,
	               AffineTransform.getScaleInstance(1.0, 0.5));
	       g.setPaint(p);
	       g.fillOval(0, 0, r, r);
	        
	       // Adds oval specular highlight at the top left
	       p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
	               getHeight() / 2.0), getWidth() / 1.4f,
	               new Point2D.Double(45.0, 25.0),
	               new float[] { 0.0f, 0.5f },
	               new Color[] { new Color(1.0f, 1.0f, 1.0f, 0.4f),
	                   new Color(1.0f, 1.0f, 1.0f, 0.0f) },
	               RadialGradientPaint.CycleMethod.NO_CYCLE);
	       g.setPaint(p);
	       g.fillOval(0, 0, r, r);
	       
	       g.translate(r/2, r/2);
		   g.setColor(Color.WHITE);
		   g.rotate(Math.toRadians(90));
		   g.setFont(new Font("Verdana", Font.PLAIN, 50));
		   FontMetrics fm = g.getFontMetrics();
		   Rectangle2D rect = fm.getStringBounds(progressCenter + "%", g);
		   int x = (0-(int)rect.getWidth())/2;
		   int y = (0-(int)rect.getHeight())/2+fm.getAscent();
		   g.drawString(progressCenter + "%", x, y);
		   
		   //Light point
		   int pSize = 75;
		   g.translate(-(this.getWidth()/2), -(this.getHeight()/2));
		   g.translate(-pSize/2, -pSize/2);
		   
		   g.translate((this.getWidth()/2), (this.getHeight()/2)-r/2-3);
		   g.rotate(-Math.toRadians(arc.getAngleExtent()), pSize/2, pSize*2);
		   p = new RadialGradientPaint(new Point2D.Double(pSize/2, pSize/2), 
				   pSize,
	               new float[] { 0.0f, 0.2f },
	               new Color[] { new Color(1.0f, 1.0f, 1.0f),
	                   new Color(1.0f, 1.0f, 1.0f, 0.0f) 
	               });

	       g.setPaint(p);
		   g.fillOval(0, 0, pSize, pSize);
	   }
	}
}

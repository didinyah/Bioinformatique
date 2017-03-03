package mainpackage.Chargement;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/*
 * 
 * Classe pour créer le fond et le cercle du milieu avec les pourcentages
 * 
 */

public class CenterCircle {
	protected int fW;
	protected int fH;
	private int progressCenter = 0;
	private int circleSize = 150;
	
	protected int inPosX;
	protected int inPosY;
	
	public CenterCircle (int lo, int la) {
		fW = lo;
		fH = la;
		
		findInPos();
   }
   
	public void findInPos(){
		inPosX = fW/2;
		inPosY = fH/2;
	}
   public void updateProgress(int progress){
	   progressCenter = progress;
   }
   
   public void drawCenterCircle(Graphics2D g){
	   int r = circleSize;
	   Paint p;
	   g.translate(fW/2, fH/2);
	   g.rotate(Math.toRadians(270));
	   Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
	      
	   arc.setFrameFromCenter(new Point(0, 0), new Point(r/2+5*r/220, r/2+5*r/220));
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
               0, fH, new Color(0.0f, 0.0f, 0.0f, 0.0f));
       g.setPaint(p);
       g.fillOval(0, 0, r, r);
       
       // Adds highlights at the bottom 
       p = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.0f),
               0, fH, new Color(1.0f, 1.0f, 1.0f, 0.4f));
       g.setPaint(p);
       g.fillOval(0, 0, r, r);
       
       // Creates dark edges for 3D effect
       p = new RadialGradientPaint(new Point2D.Double(fW / 2.0,
               fH / 2.0), fW / 2.0f,
               new float[] { 0.0f, 1.0f },
               new Color[] { new Color(6, 76, 160, 127),
                   new Color(0.0f, 0.0f, 0.0f, 0.8f) });
       g.setPaint(p);
       g.fillOval(0, 0, r, r);
        
       // Adds oval inner highlight at the bottom
       p = new RadialGradientPaint(new Point2D.Double(fW / 2.0,
    		   fH * 1.5), fW / 2.3f,
               new Point2D.Double(fW / 2.0, fW * 1.75 + 6),
               new float[] { 0.0f, 0.8f },
               new Color[] { new Color(64, 142, 203, 255),
                   new Color(64, 142, 203, 0) },
               RadialGradientPaint.CycleMethod.NO_CYCLE,
               RadialGradientPaint.ColorSpaceType.SRGB,
               AffineTransform.getScaleInstance(1.0, 0.5));
       g.setPaint(p);
       g.fillOval(0, 0, r, r);
        
       // Adds oval specular highlight at the top left
      p = new RadialGradientPaint(new Point2D.Double(fW / 2.0,
               fH / 2.0), fW,
               new Point2D.Double(45, 25),
               new float[] { 0.0f, 0.5f },
               new Color[] { new Color(1.0f, 1.0f, 1.0f, 0.4f),
                   new Color(1.0f, 1.0f, 1.0f, 0.0f) },
               RadialGradientPaint.CycleMethod.NO_CYCLE);
       g.setPaint(p);
       g.fillOval(0, 0, r, r);
       
       g.translate(r/2, r/2);
	   g.setColor(Color.WHITE);
	   g.rotate(Math.toRadians(90));
	   g.setFont(new Font("Verdana", Font.PLAIN, r/4));
	   FontMetrics fm = g.getFontMetrics();
	   Rectangle2D rect = fm.getStringBounds(progressCenter + "%", g);
	   int x = (0-(int)rect.getWidth())/2;
	   int y = (0-(int)rect.getHeight())/2+fm.getAscent();
	   g.drawString(progressCenter + "%", x, y);
	   
	   //Light point
	   int pSize = 75*circleSize/220;
	   g.translate(-(this.fW/2), -(this.fH/2));
	   g.translate(-pSize/2, -pSize/2);
	   
	   g.translate((this.fW/2), (this.fH/2)-r/2-3);
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

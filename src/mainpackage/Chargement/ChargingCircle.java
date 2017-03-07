package mainpackage.Chargement;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/*
 * 
 * Classe pour créer les cercles autour du cercle principal
 * 
 */
public class ChargingCircle {
	private int fW;
	private int fH;
	private int progressCenter = 0;
	private int circleSize = 75;
	protected float coeffX;
	protected float coeffY;
	protected float posX;
	protected float posY;
	
	protected int inPosX;
	protected int inPosY;
	protected int outPosX;
	protected int outPosY;
	
	protected String label = "";
	
	public ChargingCircle (float lo, float la, float c, float c2) {
		fW = (int)lo;
		fH = (int)la;
		coeffX = c;
		coeffY = c2;
		posX = lo*coeffX;
		posY = la*coeffY;
		
		findInPos();
		findOutPos();		
   }
   
	public void findInPos(){
		inPosX = (int)posX;
		inPosY = (int)posY;
	}
	
	public void findOutPos(){
		outPosX = (int)posX;
		outPosY = (int)posY;
	}
	
   public void updateProgress(int progress){
	   progressCenter = progress;
   }
   
   public void drawChargingCircle(Graphics2D g){
	   int r = circleSize;
	   Paint p;
	   g.translate(posX, posY);
	   g.rotate(Math.toRadians(270));
	   Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
	      
	   arc.setFrameFromCenter(new Point(0, 0), new Point(r/2+10*r/220, r/2+10*r/220));
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
               fH / 2.0), fH / 2.0f,
               new float[] { 0.0f, 1.0f },
               new Color[] { new Color(6, 76, 160, 127),
                   new Color(0.0f, 0.0f, 0.0f, 0.8f) });
       g.setPaint(p);
       g.fillOval(0, 0, r, r);
        
       // Adds oval inner highlight at the bottom
       p = new RadialGradientPaint(new Point2D.Double(fW / 2.0,
               fH * 1.5), fW / 2.3f,
               new Point2D.Double(fW / 2.0, fH * 1.75 + 6),
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
	   
	   //Light point
	   int pSize = 75*circleSize/220;
	   g.translate(r/2, r/2);
	   g.rotate(Math.toRadians(90));
	   g.translate(-(this.fW/2), -(this.fH/2));
	   g.translate(-pSize/2, -pSize/2);
	   
	   g.translate((this.fW/2), (this.fH/2)-r/2-2);
	   
	   //Texte du cercle
	   g.setColor(Color.WHITE);
	   g.setFont(new Font("Verdana", Font.BOLD, r/5));
	   g.drawString(label, -6, r-20);
	   
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

package mainpackage.Chargement;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

/*
 * 
 * Classe pour créer les Bâtons qui relient les cercles
 * 
 */
public class ChargingStick {
	protected ChargingCircle out;
	protected ChargingCircle in;
	protected CenterCircle inCenter;
	
	protected int inX;
	protected int inY;
	protected int outX;
	protected int outY;
	
	private int strokeWidth = 20;
	private int progressStick = 0;
	
	public ChargingStick(ChargingCircle outCircle, ChargingCircle inCircle){
		out = outCircle;
		in = inCircle;
		inCenter = null;
		
		outX = outCircle.outPosX;
		outY = outCircle.outPosY;
		inX = inCircle.inPosX;
		inY = inCircle.inPosY;
	}
	
	public ChargingStick(ChargingCircle outCircle, CenterCircle inCircle){
		out = outCircle;
		in = null;
		inCenter = inCircle;
		
		outX = outCircle.outPosX;
		outY = outCircle.outPosY;
		inX = inCircle.inPosX;
		inY = inCircle.inPosY;
	}
	
	public void drawChargingStick(Graphics2D g){
		GradientPaint gp = new GradientPaint(outX, outY, new Color(30, 20, 120), inX, inY, new Color(175, 175, 209));
		g.setPaint(gp);
		
		g.setStroke(new BasicStroke(strokeWidth));
		g.drawLine(outX, outY, inX, inY);
		
		moveLight(g);
	}
	
	public void updateProgress(int progress){
		   progressStick = progress;
	}
	
	public void moveLight(Graphics2D g){
		int pSize = strokeWidth*5;
		Paint p;
		g.translate(outX, outY);
		g.translate(-pSize/2, -pSize/2);
		
		double x = ((double)inX-(double)outX)*((double)progressStick/GlobalJPanel.stickSpeed);
		double y = ((double)inY-(double)outY)*((double)progressStick/GlobalJPanel.stickSpeed);
		
		g.translate(x, y);
		
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

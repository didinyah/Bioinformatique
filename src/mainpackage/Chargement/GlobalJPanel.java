package mainpackage.Chargement;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GlobalJPanel extends JPanel{
	private int fW;
	private int fH;
	
	private CenterCircle cc;
	private ArrayList<ChargingCircle> circles;
	
	public GlobalJPanel(int lo, int la){
		fW = lo;
		fH = la;
		setSize(lo, la);
		circles = new ArrayList<ChargingCircle>();
		cc = new CenterCircle(lo,la);
		createCircles();
	}
	
	public void paintComponent(Graphics g){
		//Creation des pinceaux pour chaque figure (le premier est utilisé pour le centre et le fond
		ArrayList<Graphics2D> g2 = new ArrayList<Graphics2D>();
		for(int i = 0; i < circles.size()+1; i++){
			Graphics2D t = (Graphics2D)g.create();
			t.setRenderingHints(new RenderingHints(
			    	RenderingHints.KEY_ANTIALIASING, 
			      	RenderingHints.VALUE_ANTIALIAS_ON));
			g2.add(t);
		}
	    
	    //Fond
	    GradientPaint gp = new GradientPaint(fW/2, 0, new Color(30, 20, 120), fW/2, fH, new Color(175, 175, 209));
	    gradientBackground(g2.get(0), gp);
	    
	    //Cercle du centre
	    cc.drawCenterCircle(g2.get(0));
	    g2.get(0).dispose();
	    
	    //Cercles autour
	    for(int i = 0; i < circles.size(); i++){
	    	circles.get(i).drawChargingCircle(g2.get(i+1));
	    	g2.get(i).dispose();
	    }
	}
	
	public void gradientBackground(Graphics2D g, GradientPaint gp){
		   g.setPaint(gp);
		   g.fillRect(0, 0, fW, fH);
	}
	
	public void createCircles(){
		ChargingCircle c1 = new ChargingCircle(fW, fH, 0.85f, 0.2f);
		circles.add(c1);
		ChargingCircle c2 = new ChargingCircle(fW, fH, 0.6f, 0.1f);
		circles.add(c2);
		ChargingCircle c3 = new ChargingCircle(fW, fH, 0.65f, 0.28f);
		circles.add(c3);
		ChargingCircle c4 = new ChargingCircle(fW, fH, 0.1f, 0.35f);
		circles.add(c4);
		ChargingCircle c5 = new ChargingCircle(fW, fH, 0.25f, 0.2f);
		circles.add(c5);
		ChargingCircle c6 = new ChargingCircle(fW, fH, 0.3f, 0.36f);
		circles.add(c6);
		ChargingCircle c7 = new ChargingCircle(fW, fH, 0.65f, 0.78f);
		circles.add(c7);
		ChargingCircle c8 = new ChargingCircle(fW, fH, 0.82f, 0.65f);
		circles.add(c8);
		ChargingCircle c9 = new ChargingCircle(fW, fH, 0.3f, 0.70f);
		circles.add(c9);
	}
	
	public void Resized(int w, int h){
		this.setSize(w, h);
		fW = w;
		fH = h;
		cc.fW = w;
		cc.fH = h;
        for(ChargingCircle c : circles){
			c.posX = w*c.coeffX;
			c.posY = h*c.coeffY;
		}
        repaint();
	}
	
	//Le début de l'animation se fait là
	public void startCharging(){
		new Thread(new Runnable(){
			public void run(){
				for(int i = 1; i <= 100; i++){
					try {
						cc.updateProgress(i);
						/*for(ChargingCircle c : circles){
							c.updateProgress(i);
						}*/
						repaint();
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
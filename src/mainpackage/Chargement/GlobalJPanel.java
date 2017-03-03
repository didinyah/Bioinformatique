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
	private ArrayList<ChargingStick> lines;
	
	public static int stickSpeed = 100;
	
	public GlobalJPanel(int lo, int la){
		fW = lo;
		fH = la;
		setSize(lo, la);
		circles = new ArrayList<ChargingCircle>();
		lines = new ArrayList<ChargingStick>();
		cc = new CenterCircle(lo,la);
		createCirclesAndLines();
	}
	
	public void paintComponent(Graphics g){
		//Creation des pinceaux pour chaque figure
		ArrayList<Graphics2D> g2 = new ArrayList<Graphics2D>();
		for(int i = 0; i < circles.size(); i++){
			Graphics2D t = (Graphics2D)g.create();
			t.setRenderingHints(new RenderingHints(
			    	RenderingHints.KEY_ANTIALIASING, 
			      	RenderingHints.VALUE_ANTIALIAS_ON));
			g2.add(t);
		}
		
		ArrayList<Graphics2D> g2Sticks = new ArrayList<Graphics2D>();
		for(int i = 0; i< lines.size(); i++){
			Graphics2D t = (Graphics2D)g.create();
			t.setRenderingHints(new RenderingHints(
			    	RenderingHints.KEY_ANTIALIASING, 
			      	RenderingHints.VALUE_ANTIALIAS_ON));
			g2Sticks.add(t);
		}
		
		Graphics2D g2Background = (Graphics2D)g.create();
		g2Background.setRenderingHints(new RenderingHints(
		    	RenderingHints.KEY_ANTIALIASING, 
		      	RenderingHints.VALUE_ANTIALIAS_ON));
		
		Graphics2D g2Center = (Graphics2D)g.create();
		g2Center.setRenderingHints(new RenderingHints(
		    	RenderingHints.KEY_ANTIALIASING, 
		      	RenderingHints.VALUE_ANTIALIAS_ON));
	    
	    //Fond
	    GradientPaint gp = new GradientPaint(fW/2, 0, new Color(30, 20, 120), fW/2, fH, new Color(175, 175, 209));
	    gradientBackground(g2Background, gp);
	    g2Background.dispose();
	    
	  //Bâtons qui relient les cercles
	    for(int i = 0; i < lines.size(); i++){
	    	lines.get(i).drawChargingStick(g2Sticks.get(i));
	    	g2Sticks.get(i).dispose();
	    }
	    
	    //Cercles autour
	    for(int i = 0; i < circles.size(); i++){
	    	circles.get(i).drawChargingCircle(g2.get(i));
	    	g2.get(i).dispose();
	    }
	    
	  //Cercle du centre
	    cc.drawCenterCircle(g2Center);
	    g2Center.dispose();
	}
	
	public void gradientBackground(Graphics2D g, GradientPaint gp){
		   g.setPaint(gp);
		   g.fillRect(0, 0, fW, fH);
	}
	
	public void createCirclesAndLines(){
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
		
		ChargingStick s1 = new ChargingStick(c1, c3);
		lines.add(s1);
		ChargingStick s2 = new ChargingStick(c2, c3);
		lines.add(s2);
		
		ChargingStick s3 = new ChargingStick(c4, c6);
		lines.add(s3);
		ChargingStick s4 = new ChargingStick(c5, c6);
		lines.add(s4);
		
		ChargingStick s5 = new ChargingStick(c3, cc);
		lines.add(s5);
		ChargingStick s6 = new ChargingStick(c6, cc);
		lines.add(s6);
		
		ChargingStick s7 = new ChargingStick(c7, cc);
		lines.add(s7);
		ChargingStick s8 = new ChargingStick(c8, cc);
		lines.add(s8);
		ChargingStick s9 = new ChargingStick(c9, cc);
		lines.add(s9);
	}
	
	public void Resized(int w, int h){
		this.setSize(w, h);
		fW = w;
		fH = h;
		cc.fW = w;
		cc.fH = h;
		cc.findInPos();
        for(ChargingCircle c : circles){
			c.posX = w*c.coeffX;
			c.posY = h*c.coeffY;
			c.findInPos();
			c.findOutPos();
		}

        for(ChargingStick s : lines){
        	for(int i = 0; i < circles.size(); i++){
        		if(circles.get(i).equals(s.out)){
        			s.outX = circles.get(i).outPosX;
        			s.outY = circles.get(i).outPosY;
        			break;
        		}
        	}

        	if(s.inCenter == null){
        		for(int i = 0; i < circles.size(); i++){
            		if(circles.get(i).equals(s.in)){
            			s.inX = circles.get(i).inPosX;
            			s.inY = circles.get(i).inPosY;
            			break;
            		}
            	}
        	} else {
        		s.inX = cc.inPosX;
        		s.inY = cc.inPosY;
        	}
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
						for(ChargingCircle c : circles){
							c.updateProgress(i);
						}
						for(ChargingStick s : lines){
							s.updateProgress(i);
						}
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
package mainpackage.Chargement;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mainpackage.Organism;

public class GlobalJPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextArea logFrame;
	private int fW;
	private int fH;
	
	private CenterCircle cc;
	private ArrayList<ChargingCircle> circles;
	private ArrayList<ChargingStick> lines;
	
	private Hashtable<String, ChargingCircleThread> threads = new Hashtable<String, ChargingCircleThread>();
	
	public static int stickSpeed = 100;
	public static int stickWait = 5;
	public static int circleWait = 10;
	
	private int dataCount;
	private int dataDone = 0;
	private int totalData = 0;
	protected int totalDataDone = 0;
	protected int timeEcoule = 0;
	
	private GlobalJPanel jpanel;
	private JScrollPane sp;
	private JLabel time;
	
	public boolean finished = false;
	
	public GlobalJPanel(int lo, int la, int data, JTextArea log, JScrollPane jsp, int total){
		fW = lo;
		fH = la;
		setSize(lo, la);
		circles = new ArrayList<ChargingCircle>();
		lines = new ArrayList<ChargingStick>();
		cc = new CenterCircle(lo,la);
		createCirclesAndLines();
		jpanel = this;
		dataCount = data;
		logFrame = log;
		sp = jsp;
		totalData = total;
		
		time = new JLabel("Estimation du temps de chargement : ... minutes restantes.");
		time.setForeground(Color.WHITE);
		this.add(time);
		
		Thread t = new Thread() {
			public void run() {
				while(dataDone < dataCount){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeEcoule += 200;
					setChargingTime();
				}
			}
		};
		t.start();
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
		ChargingCircle c1 = new ChargingCircle(fW, fH, 0.50f, 0.28f);
		circles.add(c1);
		ChargingCircle c2 = new ChargingCircle(fW, fH, 0.1f, 0.2f);
		circles.add(c2);
		ChargingCircle c3 = new ChargingCircle(fW, fH, 0.35f, 0.1f);
		circles.add(c3);
		ChargingCircle c4 = new ChargingCircle(fW, fH, 0.65f, 0.1f);
		circles.add(c4);
		ChargingCircle c5 = new ChargingCircle(fW, fH, 0.9f, 0.2f);
		circles.add(c5);
		
		ChargingCircle c6 = new ChargingCircle(fW, fH, 0.3f, 0.7f);
		circles.add(c6);
		ChargingCircle c7 = new ChargingCircle(fW, fH, 0.15f, 0.55f);
		circles.add(c7);
		ChargingCircle c8 = new ChargingCircle(fW, fH, 0.15f, 0.85f);
		circles.add(c8);
		ChargingCircle c9 = new ChargingCircle(fW, fH, 0.45f, 0.85f);
		circles.add(c9);
		
		ChargingCircle c10 = new ChargingCircle(fW, fH, 0.7f, 0.7f);
		circles.add(c10);
		ChargingCircle c11 = new ChargingCircle(fW, fH, 0.75f, 0.9f);
		circles.add(c11);
		ChargingCircle c12 = new ChargingCircle(fW, fH, 0.9f, 0.75f);
		circles.add(c12);
		
		ChargingStick s1 = new ChargingStick(c1, cc);
		lines.add(s1);
		ChargingStick s2 = new ChargingStick(c2, c1);
		lines.add(s2);
		ChargingStick s3 = new ChargingStick(c3, c1);
		lines.add(s3);
		ChargingStick s4 = new ChargingStick(c4, c1);
		lines.add(s4);
		ChargingStick s5 = new ChargingStick(c5, c1);
		lines.add(s5);
		
		ChargingStick s6 = new ChargingStick(c6, cc);
		lines.add(s6);
		ChargingStick s7 = new ChargingStick(c7, c6);
		lines.add(s7);
		ChargingStick s8 = new ChargingStick(c8, c6);
		lines.add(s8);
		ChargingStick s9 = new ChargingStick(c9, c6);
		lines.add(s9);
		
		ChargingStick s10 = new ChargingStick(c10, cc);
		lines.add(s10);
		ChargingStick s11 = new ChargingStick(c11, c10);
		lines.add(s11);
		ChargingStick s12 = new ChargingStick(c12, c10);
		lines.add(s12);
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
	
	//Fonctions qui selon l'élément en train d'être chargé changent le texte des cercles
	public void setElement(String s, int dataCount){
		if(s.equals("VIRUSES")){
			ArrayList<ChargingStick> a = new ArrayList<ChargingStick>();
			a.add(lines.get(1));
			a.add(lines.get(2));
			a.add(lines.get(3));
			a.add(lines.get(4));
			
			ChargingCircleThread c = new ChargingCircleThread(this, circles.get(0), lines.get(0), a, new Stack<String>(), s, dataCount);
			threads.put(s, c);
			c.start();
			
			log("Chargement du royaume \"Virus\"");
		}
		else if(s.equals("PROKARYOTES")){
			ArrayList<ChargingStick> a = new ArrayList<ChargingStick>();
			a.add(lines.get(6));
			a.add(lines.get(7));
			a.add(lines.get(8));
			
			ChargingCircleThread c = new ChargingCircleThread(this, circles.get(5), lines.get(5), a, new Stack<String>(), s, dataCount);
			threads.put(s, c);
			c.start();
			
			log("Chargement du royaume \"Prokaryotes\"");
		}
		else if(s.equals("EUKARYOTES")){
			ArrayList<ChargingStick> a = new ArrayList<ChargingStick>();
			a.add(lines.get(10));
			a.add(lines.get(11));
			
			ChargingCircleThread c = new ChargingCircleThread(this, circles.get(9), lines.get(9), a, new Stack<String>(), s, dataCount);
			threads.put(s, c);
			c.start();
			
			log("Chargement du royaume \"Eukaryotes\"");
		}
		else if(s.equals("ANALYSE")){
			ArrayList<ChargingStick> a = new ArrayList<ChargingStick>();
			a.add(lines.get(1));
			a.add(lines.get(2));
			a.add(lines.get(3));
			a.add(lines.get(4));
			
			ChargingCircleThread c = new ChargingCircleThread(this, circles.get(0), lines.get(0), a, new Stack<String>(), s, dataCount);
			threads.put(s, c);
			c.start();
			
			log("Début de l'analyse");
		}
		else if(s.equals("TELECHARGEMENT")){
			ArrayList<ChargingStick> a = new ArrayList<ChargingStick>();
			a.add(lines.get(6));
			a.add(lines.get(7));
			a.add(lines.get(8));
			
			ChargingCircleThread c = new ChargingCircleThread(this, circles.get(5), lines.get(5), a, new Stack<String>(), s, dataCount);
			threads.put(s, c);
			c.start();
			
			log("Début du téléchargement");
		}
	}
	
	public void setElement(Organism s){
		if(s.getKingdom().equals("VIRUSES")){
			if(threads.containsKey(s.getKingdom())){
				threads.get(s.getKingdom()).addStack(s.getName());
			} else{
				System.out.println("Le royaume " + s.getKingdom() + " n'existe pas.");
			}
		}
		else if(s.getKingdom().equals("PROKARYOTES")){
			if(threads.containsKey(s.getKingdom())){
				threads.get(s.getKingdom()).addStack(s.getName());
			} else{
				System.out.println("Le royaume " + s.getKingdom() + " n'existe pas.");
			}
		}
		else if(s.getKingdom().equals("EUKARYOTES")){
			if(threads.containsKey(s.getKingdom())){
				threads.get(s.getKingdom()).addStack(s.getName());
			} else{
				System.out.println("Le royaume " + s.getKingdom() + " n'existe pas.");
			}
		}
		else if(s.getKingdom().equals("ANALYSE")){
			if(threads.containsKey(s.getKingdom())){
				threads.get(s.getKingdom()).addStack(s.getName());
			} else{
				System.out.println("Le royaume " + s.getKingdom() + " n'existe pas.");
			}
		}
		else if(s.getKingdom().equals("TELECHARGEMENT")){
			if(threads.containsKey(s.getKingdom())){
				threads.get(s.getKingdom()).addStack(s.getName());
			} else{
				System.out.println("Le royaume " + s.getKingdom() + " n'existe pas.");
			}
		}
	}
	
	public void enterData(){
		dataDone++;
		cc.updateProgress(dataDone*(100/dataCount));
		if(dataDone >= dataCount){
			log("Chargement terminé.");
			totalDataDone = totalData;
			time.setText("Estimation du temps de chargement : chargement terminé.");
			finished = true;
		}
		jpanel.repaint();
	}
	
	public void setChargingTime(){
		if(totalDataDone != 0){
			int temps = timeEcoule*((totalData/totalDataDone)-1);

			if(temps != 0){
				time.setText("Estimation du temps de chargement : " + temps/100/60 + " minutes restantes.");
			}
			else{
				time.setText("Estimation du temps de chargement : moins d'une minute restante.");
			}
		}
		if(dataDone >= dataCount){
			totalDataDone = totalData;
			time.setText("Estimation du temps de chargement : chargement terminé.");
		}
	}
	
	public void log(String s){		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		logFrame.append("["+sdf.format(cal.getTime())+"]"+s+"\n");
		
		JScrollBar vertical = sp.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
}
package Windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainpackage.Chargement.ChargingCircle;
import mainpackage.Chargement.ChargingStick;

import java.awt.Graphics;

public class MainJPanel extends JPanel{
	
	private int w;
	private int h;
	
	public MainJPanel(int width, int height){
		this.w = width;
		this.h = height;
	}
	
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color color1 = new Color(22,101,191);
        Color color2 = new Color(175, 175, 209, 120);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        GradientPaint gp2 = new GradientPaint(0, 0, Color.black, 0, h, color1);
        g2d.setPaint(gp2);
        g2d.fillRect(w/2 - 2 , h/5, 2, 35*h/50);
    }
    
    public void Resized(int w, int h){
		this.w = w;
		this.h = h;
        repaint();
	}
}

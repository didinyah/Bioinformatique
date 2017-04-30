package Windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.Border;
import mainpackage.Configuration;
import mainpackage.TraitementOrganisme;
import mainpackage.TreeGestion;
import mainpackage.Chargement.Chargement;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MainWindow {

	private JFrame frame;
	private static boolean clic = false;

	/**
	 * Launch the application.
	 */
	public static void displayMainWindow() {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		MainWindow window = new MainWindow();
		window.frame.setVisible(true);
		
		while(!clic){ 
			System.out.println("");
		}
		int nbOrgaEnTout = 289 + 10*2; // nombre d'orga + nb d'analyses et nb de tï¿½lï¿½chargements
		int nbThread = 10;
		Chargement charg = new Chargement(5, nbOrgaEnTout);
		TreeGestion t = new TreeGestion();
		JCheckBoxTree tree = t.construct(charg);
		TraitementOrganisme.DLAnalyseThread(t.getListOrganism(), nbThread, t.getChargement(), tree);
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 725, 800);
		final MainJPanel mjp = new MainJPanel(frame.getWidth(), frame.getHeight());
		frame.setContentPane(mjp);
		frame.addComponentListener(new ComponentAdapter() 
		{  
	        public void componentResized(ComponentEvent evt) {
	        	mjp.Resized(frame.getWidth(), frame.getHeight());
	        }
		});
		frame.getContentPane().setBackground(new Color(255,255,255));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridBagLayout());
		
		
		
		class ImageLabel extends JLabel{
		    private Image _myimage;

		    public ImageLabel(String text){
		        super(text);
		    }

		    public void setIcon(Icon icon) {
		        super.setIcon(icon);
		        if (icon instanceof ImageIcon)
		        {
		            _myimage = ((ImageIcon) icon).getImage();
		        }
		    }

		    @Override
		    public void paint(Graphics g){
		        g.drawImage(_myimage, 0, 0, this.getWidth(), this.getHeight(), null);
		    }
		}

		ImageIcon image = new ImageIcon("files/gene1modif1.jpg");
		ImageLabel label = new ImageLabel("");
		label.setIcon(image);
		GridBagConstraints gbcimg = new GridBagConstraints();
		gbcimg.fill = GridBagConstraints.BOTH;
		gbcimg.anchor = GridBagConstraints.PAGE_START;
		gbcimg.gridx = 0;
		gbcimg.gridy = 0;
		gbcimg.weightx = 0.0;
		gbcimg.weighty = 0.1;
		gbcimg.gridwidth = 3;
		gbcimg.ipadx = 0;
		gbcimg.ipady = 0;
		gbcimg.insets = new Insets(0,0,0,0);
		frame.getContentPane().add(label,gbcimg);
		
		
		
		JLabel lblCheck = new JLabel("1. Choix des royaumes");
		Font f = new Font("Cambria", Font.BOLD, 24);
		lblCheck.setFont(f);
		lblCheck.setMinimumSize(new Dimension(300,20));
		GridBagConstraints gbclch = new GridBagConstraints();
		gbclch.fill = GridBagConstraints.NONE;
		gbclch.anchor = GridBagConstraints.CENTER;
		gbclch.gridx = 0;
		gbclch.gridy = 1;
		gbclch.weightx = 1.0;
		gbclch.weighty = 0.6;
		frame.getContentPane().add(lblCheck,gbclch);
		
		final JCheckBox chckbxEucaryotes = new JCheckBox("Eucaryotes");
		chckbxEucaryotes.setMinimumSize(new Dimension(100,20));
		chckbxEucaryotes.setForeground(new Color(37,129,235));
		chckbxEucaryotes.setOpaque(false);
		GridBagConstraints gbceuc = new GridBagConstraints();
		gbceuc.fill = GridBagConstraints.NONE;
		gbceuc.anchor = GridBagConstraints.LINE_START;
		gbceuc.gridx = 0;
		gbceuc.gridy = 2;
		gbceuc.weightx = 1.0;
		gbceuc.weighty = 0.6;
		frame.getContentPane().add(chckbxEucaryotes,gbceuc);
		chckbxEucaryotes.setBounds(8, 48, 129, 23);
		
		final JCheckBox chckbxVirus = new JCheckBox("Virus");
		chckbxVirus.setMinimumSize(new Dimension(100,20));
		chckbxVirus.setOpaque(false);
		GridBagConstraints gbcvir = new GridBagConstraints();
		gbcvir.fill = GridBagConstraints.NONE;
		gbcvir.anchor = GridBagConstraints.LINE_START;
		gbcvir.gridx = 0;
		gbcvir.gridy = 3;
		gbcvir.weightx = 1.0;
		gbcvir.weighty = 0.6;
		frame.getContentPane().add(chckbxVirus,gbcvir);
		chckbxVirus.setBounds(8, 84, 96, 23);
		
		final JCheckBox chckbxProcaryotes = new JCheckBox("Procaryotes");
		chckbxProcaryotes.setMinimumSize(new Dimension(120,20));
		chckbxProcaryotes.setOpaque(false);
		GridBagConstraints gbcpro = new GridBagConstraints();
		gbcpro.fill = GridBagConstraints.NONE;
		gbcpro.anchor = GridBagConstraints.LINE_START;
		gbcpro.gridx = 0;
		gbcpro.gridy = 4;
		gbcpro.weightx = 1.0;
		gbcpro.weighty = 0.6;
		frame.getContentPane().add(chckbxProcaryotes,gbcpro);
		chckbxProcaryotes.setBounds(8, 144, 129, 23);
		
		
		
		
		
		JLabel LblChoix = new JLabel("2. Options de lancement");
		LblChoix.setFont(f);
		LblChoix.setMinimumSize(new Dimension(300,20));
		GridBagConstraints gbcchx = new GridBagConstraints();
		gbcchx.fill = GridBagConstraints.NONE;
		gbcchx.anchor = GridBagConstraints.CENTER;
		gbcchx.gridx = 2;
		gbcchx.gridy = 1;
		gbcchx.weightx = 1.0;
		gbcchx.weighty = 0.6;
		frame.getContentPane().add(LblChoix,gbcchx);
		
		final JCheckBox chckbxKeepTxt = new JCheckBox("Garder les fichiers texte");
		chckbxKeepTxt.setOpaque(false);
		chckbxKeepTxt.setMinimumSize(new Dimension(165,20));
		GridBagConstraints gbctxt = new GridBagConstraints();
		gbctxt.fill = GridBagConstraints.NONE;
		gbctxt.anchor = GridBagConstraints.CENTER;
		gbctxt.gridx = 2;
		gbctxt.gridy = 2;
		gbctxt.weightx = 1.0;
		gbctxt.weighty = 0.6;
		frame.getContentPane().add(chckbxKeepTxt,gbctxt);
		chckbxKeepTxt.setBounds(250, 300, 196, 23);;
		
		JButton btnDl = new JButton("Télécharger");
		btnDl.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(chckbxKeepTxt.isSelected())
				{
					Configuration.OPTION_DL_KEEPFILES = true;
				}
				if(chckbxEucaryotes.isSelected())
				{
					Configuration.OPTION_DL_EUKARYOTES = true;
				}
				if(chckbxVirus.isSelected())
				{
					Configuration.OPTION_DL_VIRUSES = true;
				}
				if(chckbxProcaryotes.isSelected())
				{
					Configuration.OPTION_DL_PROKARYOTES = true;
				}
				if(Configuration.OPTION_DL_EUKARYOTES || Configuration.OPTION_DL_VIRUSES || Configuration.OPTION_DL_PROKARYOTES)
				{
					clic = true;
				}
			  }
			});
		GridBagConstraints gbcdl = new GridBagConstraints();
		gbcdl.fill = GridBagConstraints.NONE;
		gbcdl.anchor = GridBagConstraints.CENTER;
		gbcdl.gridx = 2;
		gbcdl.gridy = 3;
		gbcdl.weightx = 1.0;
		gbcdl.weighty = 0.6;
		btnDl.setBounds(277, 211, 119, 25);
		frame.getContentPane().add(btnDl,gbcdl);
		

		JLabel lblInfo = new JLabel("<HTML><U>A Propos</U></HTML>");
		Font f2 = new Font("Cambria", Font.BOLD, 10);
		lblInfo.setFont(f2);
		lblInfo.setMinimumSize(new Dimension(100,20));
		GridBagConstraints gbcinf = new GridBagConstraints();
		gbcinf.fill = GridBagConstraints.NONE;
		gbcinf.anchor = GridBagConstraints.PAGE_END;
		gbcinf.gridx = 1;
		gbcinf.gridy = 4;
		gbcinf.weightx = 1.0;
		gbcinf.weighty = 1;
		gbcimg.insets = new Insets(100,0,0,0);
		frame.getContentPane().add(lblInfo,gbcinf);
		
		
		
		/*
		JLabel lblTitre = new JLabel("Bioinfo");
		GridBagConstraints gbctit = new GridBagConstraints();
		gbctit.fill = GridBagConstraints.NONE;
		gbctit.anchor = GridBagConstraints.CENTER;
		gbctit.gridx = 1;
		gbctit.gridy = 0;
		gbctit.weightx = 0.0;
		gbctit.weighty = 0.8;
		frame.getContentPane().add(lblTitre,gbctit);
		lblTitre.setFont(new Font("Dialog", Font.BOLD, 22));
		lblTitre.setBounds((int) ((frame.getWidth()) / 2) + 190, 12, 190, 28);*/
	}
}

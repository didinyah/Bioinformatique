package Windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import mainpackage.Configuration;
import mainpackage.TraitementOrganisme;
import mainpackage.TreeGestion;
import mainpackage.Chargement.Chargement;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void displayMainWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		frame.setBounds(100, 100, 489, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JCheckBox chckbxEucaryotes = new JCheckBox("Eucaryotes");
		chckbxEucaryotes.setBounds(8, 48, 129, 23);
		frame.getContentPane().add(chckbxEucaryotes);
		
		final JCheckBox chckbxVirus = new JCheckBox("Virus");
		chckbxVirus.setBounds(8, 84, 129, 23);
		frame.getContentPane().add(chckbxVirus);
		
		final JCheckBox chckbxProcaryotes = new JCheckBox("Procaryotes");
		chckbxProcaryotes.setBounds(8, 154, 129, 23);
		frame.getContentPane().add(chckbxProcaryotes);
		
		JButton btnDl = new JButton("TÃ©lÃ©charger");
		btnDl.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
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
					int nbOrgaEnTout = 289 + 10*2; // nombre d'orga + nb d'analyses et nb de téléchargements
					int nbThread = 10;
					Chargement charg = new Chargement(5, nbOrgaEnTout);
					TreeGestion t = new TreeGestion();
					JCheckBoxTree tree = t.construct(charg);
					TraitementOrganisme.DLAnalyseThread(t.getListOrganism(), nbThread, t.getChargement(), tree);
				}
			  }
			});
		btnDl.setBounds(277, 211, 117, 25);
		frame.getContentPane().add(btnDl);
		
		JLabel lblTitre = new JLabel("Bioinfo");
		lblTitre.setFont(new Font("Dialog", Font.BOLD, 22));
		lblTitre.setBounds(18, 12, 190, 28);
		frame.getContentPane().add(lblTitre);
	}
}

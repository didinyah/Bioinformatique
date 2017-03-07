package Windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Font;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(277, 211, 117, 25);
		frame.getContentPane().add(btnNewButton);
		
		JCheckBox chckbxEucaryotes = new JCheckBox("Eucaryotes");
		chckbxEucaryotes.setBounds(8, 48, 129, 23);
		frame.getContentPane().add(chckbxEucaryotes);
		
		JCheckBox chckbxVirus = new JCheckBox("Virus");
		chckbxVirus.setBounds(8, 84, 129, 23);
		frame.getContentPane().add(chckbxVirus);
		
		JCheckBox chckbxProcaryotes = new JCheckBox("Procaryotes");
		chckbxProcaryotes.setBounds(8, 154, 129, 23);
		frame.getContentPane().add(chckbxProcaryotes);
		
		JCheckBox chckbxBacteries = new JCheckBox("Bacteries");
		chckbxBacteries.setBounds(8, 115, 129, 23);
		frame.getContentPane().add(chckbxBacteries);
		
		JLabel lblCaca = new JLabel("Bioinfo");
		lblCaca.setFont(new Font("Dialog", Font.BOLD, 22));
		lblCaca.setBounds(18, 12, 190, 28);
		frame.getContentPane().add(lblCaca);
	}
}

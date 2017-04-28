package Windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import Windows.JCheckBoxTree.CheckChangeEvent;
import Windows.JCheckBoxTree.CheckChangeEventListener;
import mainpackage.Chargement.Chargement;
import mainpackage.TreeGestion;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;

public class TreeWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TreeWindow window = new TreeWindow();
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
	public TreeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setPreferredSize(new Dimension(2000, 2000));
		frame.getContentPane().setMaximumSize(new Dimension(2000, 2000));
		frame.setBounds(100, 100, 480, 342);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(2000, 2000));
		panel.setPreferredSize(new Dimension(2000, 2000));
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {200, 400};
		gbl_panel.rowHeights = new int[] {342};
		gbl_panel.columnWeights = new double[]{1.0, 3.0};
		gbl_panel.rowWeights = new double[]{1.0};
		panel.setLayout(gbl_panel);
		
		JScrollPane cbtScrollPane = new JScrollPane();
		GridBagConstraints gbc_cbtScrollPane = new GridBagConstraints();
		gbc_cbtScrollPane.fill = GridBagConstraints.BOTH;
		gbc_cbtScrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_cbtScrollPane.gridx = 0;
		gbc_cbtScrollPane.gridy = 0;
		panel.add(cbtScrollPane, gbc_cbtScrollPane);

		Chargement charg = new Chargement(3);
		TreeGestion t = new TreeGestion();
		final JCheckBoxTree cbt = t.construct(charg);
		cbtScrollPane.setViewportView(cbt);
		
		JScrollPane listScollPane = new JScrollPane();
		GridBagConstraints gbc_listScollPane = new GridBagConstraints();
		gbc_listScollPane.fill = GridBagConstraints.BOTH;
		gbc_listScollPane.gridx = 1;
		gbc_listScollPane.gridy = 0;
		panel.add(listScollPane, gbc_listScollPane);
		
		final List list = new List();
		
		cbt.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {
            	list.removeAll();
        		for(TreePath t : cbt.getCheckedPaths())
        		{
        			String path = System.getProperty("user.dir") + "/" + t.toString().replace(",", "/").replace("[", "").replace("]","").replace(" ", "")+".xlsx";
        			File tempf = new File(path);
        			if(tempf.isFile())
        			{
        				list.add(tempf.toString());
        			}
        		}
        		this.notify();
            }           
            public void mouseEntered(MouseEvent arg0) {         
            }           
            public void mouseExited(MouseEvent arg0) {              
            }
            public void mousePressed(MouseEvent arg0) {             
            }
            public void mouseReleased(MouseEvent arg0) {
            }           
        });
		
		listScollPane.setViewportView(list);
		list.setFont(new Font("Cabin", Font.PLAIN, 12));
	}
	/*
	private void updateCheck(List list, JCheckBoxTree jtree)
	{
		list.removeAll();
		for(TreePath t : jtree.getCheckedPaths())
		{
			list.add(t.toString());
		}
		this.notify();
	}*/
}

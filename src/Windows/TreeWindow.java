package Windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
import mainpackage.Configuration;
import mainpackage.TreeGestion;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;

public class TreeWindow {

	private JFrame frame;
	private HashMap<String,String> filespath = new HashMap<String,String>();

	/**
	 * Launch the application.
	 */
	public static void displayTreeWindow(final JCheckBoxTree cbt) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TreeWindow window = new TreeWindow(cbt);
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
	public TreeWindow(final JCheckBoxTree cbt) {
		initialize(cbt);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final JCheckBoxTree cbt) {
		frame = new JFrame("Arbre des resultats");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setVisible(true);
		
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
		cbtScrollPane.setViewportView(cbt);
		
		JScrollPane listScrollPane = new JScrollPane();
		GridBagConstraints gbc_listScollPane = new GridBagConstraints();
		gbc_listScollPane.fill = GridBagConstraints.BOTH;
		gbc_listScollPane.gridx = 1;
		gbc_listScollPane.gridy = 0;
		panel.add(listScrollPane, gbc_listScollPane);
		
		;
		final List list = new List();
		final Desktop desktop = Desktop.getDesktop();
		cbt.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {
            	//Cr�ation d'un arraylist temporaire car List n'a pas de fonction sort
            	ArrayList<String> tmp = new ArrayList<String>();
            	filespath.clear();
        		for(TreePath t : cbt.getCheckedPaths())
        		{
        			String path = System.getProperty("user.dir") + Configuration.DIR_SEPARATOR + t.toString().replace(",", Configuration.DIR_SEPARATOR).replace("[", "").replace("]","").replace(" ", "");
        			File tempf = new File(path+".xlsx");
        			if(tempf.isFile() && tempf.exists() && !filespath.containsKey(tempf.getName()))
        			{
        				tmp.add(tempf.getName());
        				filespath.put(tempf.getName(), path+".xlsx");
        			}
        			else
        			{
        				String patht = (Configuration.RESULTS_FOLDER+Configuration.DIR_SEPARATOR+"Total_"+tempf.getName());
        				File tempft = new File(patht);
        				//System.out.println(patht);
        				if(tempft.isFile() && tempft.exists() && !filespath.containsKey(tempft.getName()))
        				{
        					tmp.add(tempft.getName());
            				filespath.put(tempft.getName(), patht);
        				}
        			}
        		}
            	list.removeAll();
        		tmp.sort(null);
        		for(String s : tmp)
        		{
        			list.add(s);
        		}
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
		
		list.addMouseListener(new MouseAdapter() 
		{
		    public void mouseClicked(MouseEvent evt) 
		    {
		        if (evt.getClickCount() == 2) 
		        {

		            try 
		            {
		            	desktop.open(new File(filespath.get(list.getSelectedItem())));
					} 
		            catch (Exception e) 
		            {
		            	String[] tmp = e.getMessage().split("Error message:");
		            	JOptionPane.showMessageDialog(null, "Erreur : " + tmp[tmp.length-1]);
					}
		        }
		    }
		});
		
		listScrollPane.setViewportView(list);
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

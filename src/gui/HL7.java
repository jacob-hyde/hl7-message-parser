package gui;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import models.HL7Processed;
import javax.swing.*;
import java.awt.BorderLayout;

public class HL7 {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HL7 window = new HL7();
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
	public HL7() {
		initView();
		HL7Processed get = new HL7Processed();
		get.where("uid", "=", "1");
		System.out.println(get.get());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initView() {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		this._initMenu();
		
		table = new JTable();
		frame.getContentPane().add(table, BorderLayout.CENTER);
	}
	
	private void _initMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem menuItem;
		
		menu = new JMenu("File");
		menu.setMnemonic('F');
		
		menuItem = new JMenuItem("Import");
		menu.add(menuItem);
		
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.addActionListener(new Import());
		
		menuBar.add(menu);
		
		frame.setJMenuBar(menuBar);
		
	}
	
}

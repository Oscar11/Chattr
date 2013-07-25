package com.anish.chattr.gui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;

import com.alee.laf.WebLookAndFeel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Chattr {

	public JFrame frmChattr;
	private JTextField txtPleaseEnterYour;
	private JLabel lblToGetStarted;
	private JButton btnStart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chattr window = new Chattr();
					window.frmChattr.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Chattr() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		WebLookAndFeel.install();
		frmChattr = new JFrame();
		frmChattr.setForeground(Color.BLACK);
		frmChattr.setBackground(Color.WHITE);
		frmChattr.setResizable(false);
		frmChattr.setTitle("Chattr");
		frmChattr.getContentPane().setBackground(Color.WHITE);
		frmChattr.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Chattr.class.getResource("/res/1374421217_Chat_s_copy.png")));
		label.setBounds(90, 11, 264, 118);
		frmChattr.getContentPane().add(label);
		
		txtPleaseEnterYour = new JTextField();
		txtPleaseEnterYour.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				String Name = txtPleaseEnterYour.getText();
				Name = Name.replace(' ', '_');
				txtPleaseEnterYour.setText(Name);
				if(!Name.equalsIgnoreCase("")){
					lblToGetStarted.setText("Nice to meet you! Now click Start to begin.");
					btnStart.setEnabled(true);			
				}
				else{
					lblToGetStarted.setText("Please enter your name above to get started!");
					btnStart.setEnabled(false);
					
				}
			}
		});
		
		
		txtPleaseEnterYour.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtPleaseEnterYour.setBounds(105, 152, 130, 20);
		frmChattr.getContentPane().add(txtPleaseEnterYour);
		txtPleaseEnterYour.setColumns(10);
		
		JLabel lblHello = new JLabel("Hello,");
		lblHello.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblHello.setBounds(71, 153, 64, 17);
		frmChattr.getContentPane().add(lblHello);
		
		lblToGetStarted = new JLabel("Please enter your name above to get started!");
		lblToGetStarted.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblToGetStarted.setBounds(105, 186, 264, 17);
		frmChattr.getContentPane().add(lblToGetStarted);
		
		btnStart = new JButton("Start!");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmChattr.getContentPane().removeAll();
				Storage.setName(txtPleaseEnterYour.getText());
				ChattrLoad loadScreen = new ChattrLoad(frmChattr);
			}
		});
		btnStart.setBounds(265, 214, 89, 23);
		btnStart.setEnabled(false);
		frmChattr.getContentPane().add(btnStart);
		frmChattr.setBounds(0,0,450, 300);
		frmChattr.setLocationRelativeTo(null);
		frmChattr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

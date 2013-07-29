package com.anish.chattr.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

import com.alee.laf.WebLookAndFeel;
import com.anish.chattr.protocol.Postbox;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtEnterTextHere;


	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Chattr v1.0 - Anish Basu");
		setLocationRelativeTo(null);
		WebLookAndFeel.install();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 346);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		splitPane.setContinuousLayout(true);
		contentPane.add(splitPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblPeopleOnline = new JLabel("People Online:");
		lblPeopleOnline.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panel.add(lblPeopleOnline, BorderLayout.NORTH);
		
		JScrollPane OnlinePane = new JScrollPane();
		OnlinePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(OnlinePane, BorderLayout.CENTER);
		
		JList list = new JList();
		list.setModel(Storage.OnlineModel);
		OnlinePane.setViewportView(list);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		splitPane.setLeftComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		panel_2.setBackground(Color.WHITE);
		panel_2.setBorder(null);
		panel_2.setLayout(new BorderLayout(0, 0));
		SendEvent sEvt = new SendEvent();
		txtEnterTextHere = new JTextField();
		txtEnterTextHere.addActionListener(sEvt);
		panel_2.add(txtEnterTextHere, BorderLayout.CENTER);
		txtEnterTextHere.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(sEvt);
		btnNewButton.setBackground(new Color(0, 206, 209));
		panel_2.add(btnNewButton, BorderLayout.EAST);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		JList ChatList = new JList(Storage.ChatModel);
		scrollPane.setViewportView(ChatList);
	}
	
	class SendEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Postbox.sendMessage(txtEnterTextHere.getText());
			txtEnterTextHere.setText("");
		}
	}

	
}

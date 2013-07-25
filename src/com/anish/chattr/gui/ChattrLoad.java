package com.anish.chattr.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;

import com.alee.laf.WebLookAndFeel;
import com.anish.chattr.UDPServer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChattrLoad {
	JFrame frame;
	JLabel lblLoading;
	public static volatile boolean loaded = false;
	ChattrLoad(JFrame c){
		this.frame = c;
		this.startLoading();
		
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void startLoading() {
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Chattr.class.getResource("/res/1374421217_Chat_s_copy.png")));
		label.setBounds(90, 11, 264, 118);
		frame .getContentPane().add(label);
		
		lblLoading = new JLabel();
		//Do a Load Screen
		Thread LoadTextThread = new Thread(new LoadingText());
		LoadTextThread.start();
		//Start Server
		Thread ServerThread = new Thread(new UDPServer());
		ServerThread.start();
		lblLoading.setFont(new Font("Open Sans", Font.PLAIN, 16));
		lblLoading.setBounds(174, 140, 139, 33);
		frame.getContentPane().add(lblLoading);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.repaint();
		frame.revalidate();
	}
	private class LoadingText implements Runnable{

		@Override
		public void run() {
			try{
			while(!loaded){
			lblLoading.setText("Loading.");
			Thread.sleep(3000);
			if(loaded)
				continue;
			lblLoading.setText("Loading..");
			Thread.sleep(3000);
			if(loaded)
				continue;
			lblLoading.setText("Loading...");
			Thread.sleep(3000);
			}
			frame.dispose();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainWindow frame = new MainWindow();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			}catch(InterruptedException iex){iex.printStackTrace();}
		}
		
	}
	}

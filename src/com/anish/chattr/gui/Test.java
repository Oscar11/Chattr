package com.anish.chattr.gui;

import com.anish.chattr.UDPServer;

public class Test {
	public static void main(String[] args){
		Thread t = new Thread(new UDPServer());
		t.start();
		
	}
}

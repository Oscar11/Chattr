package com.anish.chattr.gui;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

public class Storage {
	public volatile static String ownIP=""; 
	public static synchronized String getOwnIP() {
		
		if(ownIP=="")
		try{
			ownIP=InetAddress.getLocalHost().getHostAddress().toString();
		}catch(Exception e){return "0.0.0.0";}
		return ownIP;
	}
	public static byte[] key = {(byte)0x0D,(byte)0x0A,(byte)0x03,(byte)0x02,(byte)0x05,
						 (byte)0x04,(byte)0x07,(byte)0x05,(byte)0x06,(byte)0x0F,
						 (byte)0x06,(byte)0x03,(byte)0x01,(byte)0x05,(byte)0x09,
						 (byte)0x08,(byte)0x06,(byte)0x08,(byte)0x03,(byte)0x02,
						 (byte)0x09,(byte)0x09,(byte)0x07,(byte)0x0E,(byte)0x08,
						 (byte)0x05,(byte)0x0C,(byte)0x05,(byte)0x0A};
	
	public static volatile byte[] IDHash = getOwnIP().getBytes();
	static{
		for(int i=0,j=0;i<IDHash.length;i++,j++){
			j=(j==key.length)?0:j;
			IDHash[i]=(byte) (IDHash[i]^key[j]);
		}
	}
	
	
	public static volatile DefaultListModel<String> OnlineModel = new DefaultListModel<String>();
	
	public static volatile DefaultListModel<String> ChatModel = new DefaultListModel<String>();
	//Port
	private static volatile int Port=1337;
	//(User)Name
	private static volatile String Name="";
	//Chat Log
	private static volatile ArrayList<Entry> Log = new ArrayList<Entry>();
	public static volatile HashMap<String,String> Contacts = new HashMap<String,String>();
	public static synchronized String getName() {
		return Name;
	}
	
	public static synchronized void setName(String name) {
		Name = name;
	}

	public static synchronized int getPort() {
		return Port;
	}
	public static synchronized void setPort(int port) {
		Port = port;
	}
	

	public static class Entry{
		String name;
		String Message;
		public Entry(String name, String message){
			this.name = name;
			this.Message = message;
		}
		
	}
	public static synchronized void addToContacts(String IP, String Name){
		if(!Contacts.containsKey(IP)){
		Contacts.put(IP, Name);
			OnlineModel.addElement(Name);
		}
	}
	public static synchronized void deleteFromContacts(String IP, String Name){
		if(Contacts.containsKey(IP)){
		Contacts.remove(IP);
		OnlineModel.removeAllElements();
		for(String value:Contacts.values())
			OnlineModel.addElement(value);
		}
	}
	public static synchronized void addToLog(Entry ChatEntry,boolean isPM){
		Log.add(ChatEntry);
		ChatModel.addElement("<html><b>"+ChatEntry.name+"</b>: "+((isPM==true)?"<i><font color=grey>":"")+ChatEntry.Message+((isPM==true)?"</font></i>":"")+"</html>");
	}
	}

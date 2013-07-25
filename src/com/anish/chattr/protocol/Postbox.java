package com.anish.chattr.protocol;

import java.io.StringReader;
import java.util.Map;

import com.anish.chattr.UDPClient;
import com.anish.chattr.gui.Storage;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Postbox{
	public static void sendMessage(Message MessageToSend){
		UDPClient.MessageQueue.put(MessageToSend);
	}
	public static void Process(byte[] PacketData,String IP){
		Gson converter = new Gson();
		JsonReader read = new JsonReader(new StringReader(new String(PacketData)));
		read.setLenient(true);
		Message receivedMessage = converter.fromJson(read, Message.class);
		if(receivedMessage.HASH.equalsIgnoreCase(new Message(MessageProtocol.LOGIN).HASH))//Reject own Loopback Messages
			return;
		System.out.println(receivedMessage);
		switch(receivedMessage.MESSAGE_TYPE){
			case MessageProtocol.LOGIN:
				Storage.addToContacts(IP, receivedMessage.OWNER);
				UDPClient.MessageQueue.put(new Message(MessageProtocol.LOGIN_ACCEPT,Storage.getName(),IP));
				break;
			case MessageProtocol.LOGIN_ACCEPT:
				Storage.addToContacts(IP, receivedMessage.OWNER);
				break;
			case MessageProtocol.LOGOUT:
					Storage.deleteFromContacts(IP,receivedMessage.OWNER);
				break;
			case MessageProtocol.BROADCAST:
				Storage.addToLog(new Storage.Entry(receivedMessage.OWNER,receivedMessage.MESSAGE),false);
				break;
			case MessageProtocol.MESSAGE:
				Storage.addToLog(new Storage.Entry(receivedMessage.OWNER,receivedMessage.MESSAGE),true);
				break;
		}
		
	}
	
	public static void sendMessage(final String Msg){
		/*if(Msg.startsWith("/pm")){//Private Messages
			int nameInd = Msg.indexOf(' ',4);
			String Name = Msg.substring(3, nameInd);
			System.out.println(nameInd+" "+Name);
		//UDPClient.MessageQueue.put(new Message)
		}*/
		Thread SendThread = new Thread(new Runnable(){
			
			@Override
			public void run() {
				if(Msg.startsWith("/pm")){//Private Messages
					int nameInd = Msg.indexOf(' ',4);
					String Name = Msg.substring(3, nameInd).trim();
					String SendMsg = Msg.substring(nameInd);
					if(Storage.Contacts.containsValue(Name.trim())){
						Storage.addToLog(new Storage.Entry(Storage.getName(), SendMsg), true);
						String IP="";
						for(Map.Entry<String, String> pair: Storage.Contacts.entrySet()){
							if(pair.getValue().equalsIgnoreCase(Name))
							{
								IP = pair.getKey();
							}
						}
						UDPClient.MessageQueue.put(new Message(MessageProtocol.MESSAGE,null,SendMsg,IP));
					}
					else if(Storage.getName().equalsIgnoreCase(Name)){
						Storage.addToLog(new Storage.Entry("Server", "PMing yourself doesn't work. Trust me."), true);
					}
					else{
						
						Storage.addToLog(new Storage.Entry("Server", "Can't PM an unknown Contact, Sorry!"), true);
					}
					}
				if(Msg.startsWith("/help")){
					Storage.addToLog(new Storage.Entry("Server", "Help Menu:"), true);
					Storage.addToLog(new Storage.Entry("Server", "/pm <Name> PMs a User."), true);
					Storage.addToLog(new Storage.Entry("Server", "/help This menu"), true);
				}
				if(Msg.startsWith("/clear")){
					Storage.ChatModel.clear();
				}
				else{
					Storage.addToLog(new Storage.Entry(Storage.getName(), Msg), false);
					UDPClient.MessageQueue.put(new Message(MessageProtocol.BROADCAST,null,Msg));
				}
			}
			
		});
		SendThread.start();
	}
}

package com.anish.chattr.protocol;


import com.anish.chattr.gui.Storage;

public class Message {
	public int MESSAGE_TYPE;
	public String OWNER;
	public String MESSAGE;
	public String IP;
	public String HASH = new String(Storage.IDHash);
	/*
	 * @param int type - Message type
	 * @param String Owner[Pass null if Owner]
	 * @param String Message(Optional - Broadcast/Message types)
	 * @param String IP(Optional - Message/LoginAccept)[Pass null if Owner]
	 */
	public Message(int type,Object... parameters){
		this.MESSAGE_TYPE = type;
		try{
		switch(type){
		case MessageProtocol.LOGIN:
			this.OWNER = Storage.getName();
			break;
			
		case MessageProtocol.LOGOUT:
			this.OWNER = Storage.getName();
			if(parameters[0] instanceof String)
			this.IP = (String)parameters[0];
			break;
			
		case MessageProtocol.BROADCAST:
			if(parameters[0] instanceof String)//Name
				this.OWNER = (String)parameters[0];
			else
				this.OWNER = Storage.getName();
			
			if(parameters[1] instanceof String)//Message
				this.MESSAGE = (String)parameters[1];
			else
				this.MESSAGE = "";
			break;
			
		case MessageProtocol.LOGIN_ACCEPT:
			if(parameters[0] instanceof String)//Name
				this.OWNER = (String)parameters[0];
			else
				this.OWNER = Storage.getName();
			
			if(parameters[1] instanceof String)//Message
				this.IP = (String)parameters[1];
			break;
			
		case MessageProtocol.MESSAGE:
			if(parameters[0] instanceof String)//Name
				this.OWNER = (String)parameters[0];
			else
				this.OWNER = Storage.getName();
			
			if(parameters[1] instanceof String)//Message
				this.MESSAGE = (String)parameters[1];
			
			if(parameters[2] instanceof String)//IP
				this.IP = (String)parameters[2];
			break;
		}
		}catch(ArrayIndexOutOfBoundsException ex){}
	}
	public String toString(){
		return "TYPE="+MESSAGE_TYPE+" OWNER = "+OWNER+" MESSAGE="+MESSAGE+" IP="+IP+" HASH="+HASH;
		
	}
}

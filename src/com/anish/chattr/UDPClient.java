package com.anish.chattr;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.anish.chattr.gui.Storage;
import com.anish.chattr.protocol.Message;
import com.anish.chattr.protocol.MessageProtocol;
import com.google.gson.Gson;

public class UDPClient implements Runnable{
	public static volatile TaskQueue<Message> MessageQueue = new TaskQueue<Message>();
	DatagramSocket ChatSocket;
	UDPClient(DatagramSocket CSocket){
		this.ChatSocket = CSocket;
	}
	@Override
	public void run() {
		Gson converter = new Gson();
		try{
		while(true){
			if(MessageQueue.isEmpty())
				continue;//Nothing to Send
			Message sendMsg = MessageQueue.get();
			System.out.println(sendMsg);
			if(sendMsg.MESSAGE_TYPE == MessageProtocol.MESSAGE||sendMsg.MESSAGE_TYPE==MessageProtocol.LOGIN_ACCEPT){
				byte[] sendData = converter.toJson(sendMsg).getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,InetAddress.getByName(sendMsg.IP),Storage.getPort());
				ChatSocket.send(sendPacket);
			}
			else{
				// Get Packet as Byte array
				byte[] sendData = converter.toJson(sendMsg).getBytes();

				// Try 255.255.255.255
				try {
					// Send Hello Packet
					DatagramPacket HELLOPacket = new DatagramPacket(sendData,
							sendData.length,
							InetAddress.getByName("255.255.255.255"),
							Storage.getPort());
					ChatSocket.send(HELLOPacket);
				} catch (Exception e) {/* Silently let the error die */
				}

				// Try all other Interfaces
				Enumeration<NetworkInterface> interfaces = NetworkInterface
						.getNetworkInterfaces();
				while (interfaces.hasMoreElements()) {
					NetworkInterface netInterface = interfaces.nextElement();

					if (netInterface.isLoopback() || !netInterface.isUp()) {
						continue;
					}
					// Send to all Addresses
					for (InterfaceAddress interAddress : netInterface
							.getInterfaceAddresses()) {
						InetAddress broadcastAddress = interAddress.getBroadcast();
						if (broadcastAddress == null) {
							continue;
						}

						// Broadcast OTN
						try {
							// Send Hello Packet
							DatagramPacket HELLOPacket = new DatagramPacket(
									sendData, sendData.length, broadcastAddress,
									Storage.getPort());
							ChatSocket.send(HELLOPacket);
						} catch (Exception e) {/* Silently let the error die */
						}

					}

				}
			}
		}
		}catch(Exception e){e.printStackTrace();}
	}
}

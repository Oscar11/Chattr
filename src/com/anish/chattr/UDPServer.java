package com.anish.chattr;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.anish.chattr.gui.ChattrLoad;
import com.anish.chattr.gui.Storage;
import com.anish.chattr.protocol.Message;
import com.anish.chattr.protocol.MessageProtocol;
import com.anish.chattr.protocol.Postbox;
import com.google.gson.Gson;

public class UDPServer implements Runnable {
	public DatagramSocket ChatSocket;

	@Override
	public void run() {
		Gson convert = new Gson();

		try {
			// ----------------
			// Login Protocol
			// ----------------
			ChatSocket = new DatagramSocket(Storage.getPort(),InetAddress.getByName("0.0.0.0"));
			ChatSocket.setBroadcast(true);
			// Get JSON Interpretation
			String HELLO = convert.toJson(new Message(MessageProtocol.LOGIN));
			// Get Packet as Byte array
			byte[] HelloData = HELLO.getBytes();

			// Try 255.255.255.255
			try {
				// Send Hello Packet
				DatagramPacket HELLOPacket = new DatagramPacket(HelloData,
						HelloData.length,
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

					// Send Hello to Broadcast OTN
					try {
						// Send Hello Packet
						DatagramPacket HELLOPacket = new DatagramPacket(
								HelloData, HelloData.length, broadcastAddress,
								Storage.getPort());
						ChatSocket.send(HELLOPacket);
					} catch (Exception e) {/* Silently let the error die */
					}

				}

			}
			//--------------------
			// Start Server
			//--------------------
			ChattrLoad.loaded = true;
			Thread Client = new Thread(new UDPClient(ChatSocket));
			Client.start();
			while(true){
				//Receive Packet
				byte[] recieveBuffer = new byte[65536];
				DatagramPacket receivePacket = new DatagramPacket(recieveBuffer,recieveBuffer.length);
				ChatSocket.receive(receivePacket);
				Postbox.Process(receivePacket.getData(),receivePacket.getAddress().getHostAddress());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

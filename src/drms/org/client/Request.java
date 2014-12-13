/**
 * @author Amritansh
 * @Description Final Project : Software Failure Tolerant and/or Highly Available Distributed Reservation Management System
 * Client Process forms the string message to be sent in the form of a payload and uses udp to communicate with the front end
 * 
 */
package drms.org.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import drms.org.util.Configuration;

public class Request {
	

	
	
	
	private DatagramSocket clientSocket;
	/**
	 * The request and response will share the socket.
	 * @param datagramSocket
	 */
	public Request(DatagramSocket datagramSocket){
		clientSocket = datagramSocket;
	}
	
	
	/**
	 * Sends message to  
	 * @param portNumber
	 * @param data
	 * @return server response
	 */
	public void send(String data) {
		String response = null;
		try {
			byte[] receiveData = new byte[102400];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			byte[] sendData = new byte[102400];
			sendData = data.getBytes();
			System.out.println("*********UDP@CLIENT IN OPERATION*********\n");
			System.out.println("Size of data to be sent :" + data.length() + " bytes");
			System.out.print("CLIENT : Ready to send data " + "\n");
			clientSocket.send(new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(), Configuration.FRONTEND_PORT_NUMBER));
			//@todo remember to un-comment this line 
			//clientSocket.setSoTimeout(10000000);
			try {
				clientSocket.receive(receivePacket);
				response =  new String(receivePacket.getData());
				InetAddress returnIPAddress = receivePacket.getAddress();
				System.out.println("From FrontEnd at: " + returnIPAddress + ":" + receivePacket.getPort());
				System.out.println("Message from Front End: " + response);
			} catch (final SocketTimeoutException ste) {
				System.out.println("Timeout Occurred: Packet assumed lost");
			}
		} catch (final SocketException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}



}

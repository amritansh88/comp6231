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
import java.net.UnknownHostException;

import drms.org.util.Configuration;

public class ClientProcess {
	//@deprecated
	final static int udpClientPortNumber = 1098;
	//@deprecated 
	static InetAddress IPAddress;

	ClientProcess() {
		try {
			IPAddress = InetAddress.getLocalHost();
		} catch (final UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends message to  
	 * @param portNumber
	 * @param data
	 * @return server response
	 */
	public String sendData(String data) {
		String fronEndResponse = null;
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
			byte[] receiveData = new byte[102400];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			byte[] sendData = new byte[102400];
			sendData = data.getBytes();
			System.out.println("*********UDP@CLIENT IN OPERATION*********\n");
			System.out.println("Size of data to be sent :" + data.length() + " bytes");
			System.out.print("CLIENT : Ready to send data " + "\n");
			clientSocket.send(new DatagramPacket(sendData, sendData.length, IPAddress, Configuration.FRONTEND_PORT_NUMBER));
			clientSocket.setSoTimeout(10000000);
			try {
				clientSocket.receive(receivePacket);
				fronEndResponse =  new String(receivePacket.getData());
				InetAddress returnIPAddress = receivePacket.getAddress();
				System.out.println("From FrontEnd at: " + returnIPAddress + ":" + receivePacket.getPort());
				System.out.println("Message from Front End: " + fronEndResponse);
			} catch (final SocketTimeoutException ste) {
				System.out.println("Timeout Occurred: Packet assumed lost");
			}
		} catch (final SocketException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}finally{
			clientSocket.close();
		}
		return fronEndResponse;
	}



}

/**
 * @author Amritansh
 * @Description Final Project : Software Failure Tolerant and/or Highly Available Distributed Reservation Management System
 * FrontEnd Interface Implementation 
 * 
 */
package drms.org.frontend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import drms.org.util.Configuration;

public class FrontEndImpl  {

	final static int udpClientPortNumber = 1098;
	final static int RM_PORT_NUMBER = 2525;;
	static InetAddress IPAddress;
	static int uniqueID = 0;
	
	
	/**
	 * Available sockets 
	 */
	private DatagramSocket clientCommunicationSocket;
	private DatagramSocket sequencerCommunicationSocket;

	
	
	
	private HashMap<Integer, String> messageMap = new HashMap<Integer, String>();
	FrontEndImpl(DatagramSocket clientToFrontEndSocket, DatagramSocket sequencerToFrontEndSocket) {
		clientCommunicationSocket = clientToFrontEndSocket;
		sequencerCommunicationSocket = sequencerToFrontEndSocket;
		try {
			IPAddress = InetAddress.getLocalHost();
		} catch (final UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description : validate server response and inform replica manager
	 */
	public String validateServerResponse(String response1, String response2, String response3) {
		if (response1.equals(response2) && response1.equals(response3)) {
			sendClientResponse(udpClientPortNumber, response1);
		}
		sendClientResponse(udpClientPortNumber, response1);
		// incase not send information to replica manager
		return null;
	}

	/**
	 * Description : Receive data from server
	 */
	public String receiveServerResponse() {
		String serverResponse = null;
		String serverResponse1 = null;
		String serverResponse2 = null;
		String serverResponse3 = null;
		try {
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(new byte[Configuration.BUFFER_SIZE], Configuration.BUFFER_SIZE);
				System.out.println("\n");
				System.out.println("Waiting for datagram packet from Server");
				//@todo change this mechanism, it is not good to this way 
				clientCommunicationSocket.receive(receivePacket);
				String serverMessage = new String(receivePacket.getData());
				System.out.println("From FrontEnd: " + IPAddress + ":" + RM_PORT_NUMBER);
				System.out.println("Message: " + serverMessage);
				serverResponse = serverMessage;
				validateServerResponse(serverResponse1, serverResponse2, serverResponse3);
				System.out.println(serverResponse);
			}
		} catch (final SocketException ex) {
			System.out.println("UDP Port is occupied.");
			System.exit(0);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		//@todo remove returned data 
		return serverResponse;
	}

	public void sendClientResponse(int portNumber, String data) {
		try {

			DatagramSocket frontEndSocket = new DatagramSocket();
			byte[] sendData = new byte[102400];
			sendData = data.getBytes();
			System.out.println("Size of data to be sent :" + data.length() + " bytes");
			System.out.print("CLIENT : Ready to send data " + "\n");
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNumber);
			frontEndSocket.send(sendPacket);
			frontEndSocket.setSoTimeout(10000000);
			frontEndSocket.close();

		} catch (final SocketException e) {

			e.printStackTrace();
		} catch (final IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Description: Receives Message from client
	 * @deprecated this function is going to be replaced with ClientMessageListener process 
	 * @param portNumber
	 * @return Acknowledgement to client
	 */
	public String receiveClientMessage() {
		String clientRequestMessage = null;
		try {

			byte[] receiveData = new byte[1024];
			while (true) {
				receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				System.out.println("*********UDP@SEQUENCER IN OPERATION*********");
				System.out.println("\n");
				System.out.println("Waiting for datagram packet from client");
				clientCommunicationSocket.receive(receivePacket);
				clientRequestMessage = new String(receivePacket.getData());
				InetAddress IPAddress = receivePacket.getAddress();
				System.out.println("From : " + IPAddress + ":" + udpClientPortNumber);
				System.out.println("Message received from Client: " + clientRequestMessage);
				sendSequencerMessage(Configuration.FRONTEND_PORT_NUMBER, clientRequestMessage);
			}
		} catch (final SocketException ex) {
			System.out.println("UDP Port is occupied.");
			System.exit(0);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return clientRequestMessage;
	}

	/**
	 * Description: Update a HashMap with the message response returned by the
	 * sequencer
	 * 
	 * @param key
	 * @param value
	 */
	public void UpdateMessageMap(int key, String value) {

		messageMap.put(key, value);
	}

	/**
	 * Description: Send message to sequencer
	 * 
	 * @param portNumber
	 * @param data
	 */
	public String sendSequencerMessage(int portNumber, String data) {
		String response = null;
		try {
			DatagramSocket frontEndSocket = new DatagramSocket();
			byte[] receiveData = new byte[102400];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			byte[] sendData = new byte[102400];
			sendData = data.getBytes();
			System.out.println("Data sent to the sequencer is of size :" + data.length() + "bytes");
			System.out.print("Ready to send data to Sequencer" + "\n");
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNumber);
			frontEndSocket.send(sendPacket);
			frontEndSocket.setSoTimeout(10000);
			try {
				frontEndSocket.receive(receivePacket);
				response = new String(receivePacket.getData());
				String[] arrayString = response.split("=");
				String newString = arrayString[0].replaceAll("\\{", "");
				int key = Integer.parseInt(newString);
				String value = arrayString[1];
				UpdateMessageMap(key, value);
				InetAddress returnIPAddress = receivePacket.getAddress();
				System.out.println("From Sequencer at: " + returnIPAddress + ":" + portNumber);
				System.out.println("Message: " + response);
			} catch (final SocketTimeoutException ste) {
				System.out.println("Timeout Occurred: Packet assumed lost");
			}
			frontEndSocket.close();
		} catch (final SocketException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return response;
	}

}

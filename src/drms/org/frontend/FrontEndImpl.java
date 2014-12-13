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
import java.util.HashMap;
import java.util.Map;

import drms.org.model.NetworkMessage;
import drms.org.model.Traffic;
import drms.org.util.Configuration;

public class FrontEndImpl  {
	
	static int uniqueID = 0;
	
	/**
	 * The traffic will record all  acknowledgments
	 */
	private Map<String, Traffic> acknowledgments;
	/**
	 * the traffic will keep track of all sent messages.
	 * It is the same thing as in Sequencer 
	 */
	private Map<String, Map<String, NetworkMessage>> traffic;

	
	
	//will be used by external accessors
	public Map<String, Traffic> getAcknowledgments() {
		return acknowledgments;
	}
	
	/**
	 * getTraffic is used to check message arrival, and make decision to forward messages to client after N messages arrived
	 * @return
	 */
	public Map<String,Map<String, NetworkMessage>> getTraffic(){
		return traffic;
	}
	 
			
	
	/**
	 * Available sockets 
	 */
	private DatagramSocket clientCommunicationSocket;
	private HashMap<Integer, String> messageMap = new HashMap<Integer, String>();

	FrontEndImpl(DatagramSocket clientToFrontEndSocket, DatagramSocket sequencerToFrontEndSocket) {
		clientCommunicationSocket = clientToFrontEndSocket;
		acknowledgments = new HashMap<String,Traffic>(); 
		traffic = new HashMap<String, Map<String, NetworkMessage>>();

	}

	/**
	 * Description : validate server response and inform replica manager
	 */
	public String validateServerResponse(String response1, String response2, String response3) {
		if (response1.equals(response2) && response1.equals(response3)) {
			sendClientResponse(Configuration.CLIENT_PORT_NUMBER, response1);
		}
		sendClientResponse(Configuration.CLIENT_PORT_NUMBER, response1);
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
				System.out.println("From FrontEnd: " + InetAddress.getLocalHost() + ":" + Configuration.RM_PORT_NUMBER);
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
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(), portNumber);
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
				System.out.println("From : " + IPAddress + ":" + Configuration.CLIENT_PORT_NUMBER);
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
	 * Description: Update a HashMap with the message response returned by the sequencer
	 * 
	 * @param key
	 * @param value
	 */
	public void UpdateMessageMap(int key, String value) {

		messageMap.put(key, value);
	}

	/**
	 * Description: Send message to sequencer
	 * @deprecated This function has been replaced by ClientMessageListener 
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
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(), portNumber);
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

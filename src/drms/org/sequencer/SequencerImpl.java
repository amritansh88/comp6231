/**
 * @author Amritansh
 * @Description Final Project : Software Failure Tolerant and/or Highly Available Distributed Reservation Management System
 * Sequencer Interface Implementation 
 * 
 */
package drms.org.sequencer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import drms.org.model.NetworkMessage;
import drms.org.util.Configuration;

public class SequencerImpl implements SequencerInterface {

	static int uniqueID = 0;
	final static int udpFrontEndPortNumber = 223;
	/** replica manager port number **/
	final static int REMOTE_PORT_NUMBER = 2626;
	final static int MULTICAST_PORT_NUMBER = 3456;
	
	
	static InetAddress IPAddress;
	static InetAddress RemoteIPAddress;
	private DatagramSocket replicaManagerSocket;

	/**
	 * Map of nodes to send messages to, and listen to 
	 */
	private Map<String, InetAddress> nodes; 
	private HashMap<Integer, String> idMap = new HashMap<Integer, String>();
	
	/**
	 * This section will keep track of sent, and received messages.
	 * Map<String, Map<String, NetworkMessage>>
	 * 	   String : operation number 
	 * 		Map<String, NetworkMessage>: Replica Address, NetworkMessage sent 
	 */
	private Map<String, Map<String, NetworkMessage>> traffic;
	
	
	/**
	 * @param replicaSocket
	 * @throws UnknownHostException
	 */
	SequencerImpl(DatagramSocket replicaSocket) throws UnknownHostException {
		replicaManagerSocket = replicaSocket;
		IPAddress = InetAddress.getLocalHost();
		RemoteIPAddress = InetAddress.getByName(Configuration.RM_ONE);//
		nodes = new HashMap<String, InetAddress>();
		for( String node : Configuration.CLUSTER ){
			nodes.put( node, InetAddress.getByName(node) );
		}
		//
		traffic = new HashMap<String, Map<String, NetworkMessage>>();
	}

	
	/**
	 * @return traffic
	 */
	public Map<String, Map<String, NetworkMessage>> getTraffic(){
		return traffic;
	}
	
	/**
	 * @return
	 */
	public Map<String, InetAddress> getClusterNodes(){
		return nodes;
	}
	
	/**
	 * Description: Multicast message to all the replica managers
	 * @deprecated this this function has to be replaced with implementation at FrontEndMessageListener process 
	 * @param portNumber
	 * @param data
	 */
	public void sendMulticastMessage(String data) {
		DatagramSocket sequencerSocket = null;
		try {
			sequencerSocket = new DatagramSocket();
			byte[] sendData = new byte[102400];
			sendData = data.getBytes();
			System.out.println("Size of data to be sent :" + data.length() + " bytes");
			System.out.print("Sequencer: Ready to send data " + "\n");
			for( Entry<String, InetAddress> node  : nodes.entrySet() ){
				if( !traffic.containsKey(String.valueOf(uniqueID)) ){
					traffic.put(String.valueOf(uniqueID), new HashMap<String, NetworkMessage>() );
				}
				/**
				 * @todo this has to be re-done 
				 * @todo sendData, use a transformer to send a networkmessage instead 
				 */
				traffic.get(String.valueOf(uniqueID)).put(node.getKey(), new NetworkMessage("", "", ""));
				sequencerSocket.send(new DatagramPacket(sendData, sendData.length, node.getValue(), Configuration.RM_PORT_NUMBER));
			}
			sequencerSocket.setSoTimeout(10000000);
		} catch (final SocketException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (sequencerSocket != null && !sequencerSocket.isClosed()) {
				sequencerSocket.close();
			}

		}
	}

	/**
	 * Description : Creates a hashmap of unique id and message which needs to
	 * be forwarder to the replica manager
	 * 
	 * @param frontEndMessage
	 * @return hashmap returned as string
	 */
	public String addMessageID(String frontEndMessage) {
		uniqueID = uniqueID + 1;
		System.out.println("Message added to map");
		System.out.println("frontEndMessage :" + frontEndMessage);
		String updatedFrontEndMessage = String.format("{\"id\":\"%d\", Front End Message:\"%s\"}", uniqueID, frontEndMessage);
		sendMulticastMessage(updatedFrontEndMessage);
		System.out.println("uniqueID :" + uniqueID);
		idMap.put(uniqueID, updatedFrontEndMessage);
		System.out.println("Map looks like this :" + idMap.toString().replaceAll("\\{", ""));
		return idMap.toString();
	}

	/**
	 * Description: Receives message from front end
	 * @deprecated have to be replaced by FrontEndMessageListener instead 
	 * @param portNumber
	 */
	public String receiveFrontEndMessage(int portNumber) {
		String sequencerResponse = null;
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket();
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			while (true) {
				receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				System.out.println("*********UDP@SEQUENCER IN OPERATION*********");
				System.out.println("\n");
				System.out.println("Waiting for datagram packet from Front End");
				serverSocket.receive(receivePacket);
				String frontEndMessage = new String(receivePacket.getData());
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				System.out.println("From FrontEnd: " + IPAddress + ":" + portNumber);
				System.out.println("Message: " + frontEndMessage);
				sequencerResponse = frontEndMessage;
				addMessageID(frontEndMessage);
				sendData = idMap.toString().getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}
		} catch (final SocketException ex) {
			System.out.println(String.format("UDP Port is occupied OR %s ", ex.getMessage()));
			//System.exit(0);
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		}
		return sequencerResponse;
	}

	/**
	 * @todo test and replace this method, if not performing well.
	 * @return
	 */
	public int getUniqueId() {
		return uniqueID + 1;
	}

}
package drms.org.frontend;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

import drms.org.model.NetworkMessage;
import drms.org.model.Traffic;
import drms.org.util.Configuration;
import drms.org.util.MessageListener;
import drms.org.util.NetworkMessageParser;

/**
 * The ClientMessageListener : 
 * 	- Captures messages from Client
 *  - Forwards the Message to Sequencer 
 *  - Waits for Acknowledgment from the Sequencer
 * 	- Register received Acknowledgment messages into traffic tracker 
 *  - Starts a new Thread for ReplicaManagerListener(), which expires after N minutes
 */
public class ClientMessageListener implements MessageListener {

	private FrontEndImpl frontEnd;
	private DatagramSocket socket;
	private DatagramSocket datagramSocket;
	public ClientMessageListener(FrontEndImpl frontEndImpl, DatagramSocket feSocket) {
		socket = feSocket;
		frontEnd = frontEndImpl;
	}

	@Override
	public void onMessage() {
			System.out.println("*********FrontEnd is Listening to Client messages *********");
			while (true) {
				try{
					System.out.println("Waiting for datagram packet from client");
					DatagramPacket receivePacket = new DatagramPacket(new byte[Configuration.BUFFER_SIZE], new byte[Configuration.BUFFER_SIZE].length);
					socket.receive(receivePacket);
					NetworkMessage networkMessage = NetworkMessageParser.parse(new String(receivePacket.getData()));
					String message =  networkMessage.getPayload();
					datagramSocket = new DatagramSocket();
					datagramSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getLocalHost(), Configuration.SEQUENCER_PORT_NUMBER));
					System.out.println( String.format("ClientMessageListener::onMessage %s ", new String(receivePacket.getData()) ) );
					receivePacket = new DatagramPacket(new byte[Configuration.BUFFER_SIZE], new byte[Configuration.BUFFER_SIZE].length);
					datagramSocket.receive(receivePacket);
					NetworkMessage trafficMessage = NetworkMessageParser.parse( new String(receivePacket.getData()) );
					Traffic traffic = NetworkMessageParser.parseTraffic( trafficMessage );
					networkMessage.setId(traffic.getMessageId());
					trafficMessage.setDestination(networkMessage.getDestination());
					frontEnd.getTraffic().put(networkMessage.getId(), new HashMap<String,NetworkMessage>());
					frontEnd.getAcknowledgments().put(traffic.getMessageId(), traffic);
				}catch( Exception e ){
					e.printStackTrace();
				}
			}
	}

	@Override
	public void run() {
		onMessage();
	}

}

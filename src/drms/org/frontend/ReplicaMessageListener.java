package drms.org.frontend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

import drms.org.model.NetworkMessage;
import drms.org.util.Configuration;
import drms.org.util.MessageListener;
import drms.org.util.NetworkMessageParser;

/**
 * This listener runs a live process.
 * The process captures messages sent by the ReplicaManager. 
 * 	- Each RM sends back a message as a response to the request.
 *  - Upon Reception, ReplicaMessageListener updates the FrontEnd's <code>Map<String, Map<String, NetworkMessage>> traffic </code>
 *  - After N-Receptions compare results and send Unique response back to client 
 *  - @todo Another process, will detect if the list of required messages to notify the client is reached 
 */
public class ReplicaMessageListener implements Runnable, MessageListener {

	private FrontEndImpl frontEnd;
	private DatagramSocket socket;
	public ReplicaMessageListener(FrontEndImpl frontEndImpl, DatagramSocket replicaToFrontEndSocket) {
		frontEnd = frontEndImpl;
		socket = replicaToFrontEndSocket;
	}

	
	@Override
	public void onMessage() {
		while(true){
			DatagramPacket datagramPacket = new DatagramPacket(new byte[Configuration.BUFFER_SIZE], Configuration.BUFFER_SIZE);
			try {
				socket.receive( datagramPacket );
				NetworkMessage networkMessage = NetworkMessageParser.parse( new String( datagramPacket.getData()) );
				if (!frontEnd.getTraffic().containsKey(networkMessage.getId())) {
					frontEnd.getTraffic().put(networkMessage.getId(), new HashMap<String, NetworkMessage>());
				}
				frontEnd.getTraffic().get(networkMessage.getId()).put(networkMessage.getReplica(), NetworkMessageParser.parse(new String(datagramPacket.getData())));
				//@todo check if the traffic received all messages from the RMs
				//@todo take a decision on which message to send to the clients.
				//@todo if a RM has the wrong message, send a notification.
				socket.send(new DatagramPacket(datagramPacket.getData(), datagramPacket.getData().length, InetAddress.getLocalHost(), Configuration.CLIENT_PORT_NUMBER));
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}

	@Override
	public void run() {
		onMessage();
	}

}
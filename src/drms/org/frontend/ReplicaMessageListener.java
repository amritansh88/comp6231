package drms.org.frontend;

import java.net.DatagramSocket;

import drms.org.util.MessageListener;

/**
 * This listener runs a live process.
 * The process captures messages sent by the ReplicaManager. 
 * 	- Each RM sends back a message as a response to the request.
 *  - Upon Reception, ReplicaMessageListener updates the FrontEnd's <code>Map<String, Map<String, NetworkMessage>> traffic </code>
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
			/**
			 * grab the message
			 * do validation 
			 * send the message to 
			 */
			
		}
	}

	@Override
	public void run() {
		onMessage();
	}

}

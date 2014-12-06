package drms.org.frontend;

import java.net.DatagramSocket;

import drms.org.util.MessageListener;




/**
 * This listener has to collect data from ReplicaManager, and send those to the the client, after the FE validates them  
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

package drms.org.frontend;

import java.net.DatagramSocket;
import java.net.SocketException;

import drms.org.util.Configuration;

public class FrontEndDriver extends FrontEndImpl{
	


	FrontEndDriver(DatagramSocket datagramSocket, DatagramSocket sequencerToFrontEndSocket) {
		super(datagramSocket, sequencerToFrontEndSocket);
	}

	public static void main(String args[]) throws SocketException
	{
		/**
		 * @todo the front end has to receive messages from Replica Managers.
		 * @todo add a ReplicMessageListener 
		 */
		DatagramSocket clientToFrontEndSocket = new DatagramSocket(Configuration.FRONTEND_PORT_NUMBER);
		DatagramSocket sequencerToFrontEndSocket = new DatagramSocket(Configuration.SEQUENCER_TO_FRONTEND_PORT_NUMBER);
		FrontEndImpl frontEnd = new FrontEndImpl(clientToFrontEndSocket, sequencerToFrontEndSocket);
		System.out.println("**************FrontEnd is up and Running*************");
		System.out.println("**************Data would be forwarded to Sequencer*************");
		(new Thread( new ClientMessageListener(frontEnd, clientToFrontEndSocket))).start(); 
		(new Thread( new ReplicaMessageListener(frontEnd, clientToFrontEndSocket))).start();
	}
}

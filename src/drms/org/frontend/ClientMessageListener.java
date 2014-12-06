package drms.org.frontend;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import drms.org.util.Configuration;
import drms.org.util.MessageListener;

/**
 * The client listener will be capturing messages coming from the client, 
 * 	- Register those in traffic tracker 
 *  - and forward those messages to the sequencer 
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
					String message = new String(receivePacket.getData()) ;
					datagramSocket = new DatagramSocket();
					datagramSocket.send( new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getLocalHost(), Configuration.SEQUENCER_PORT_NUMBER));
					System.out.println( String.format("ClientMessageListener::onMessage %s ", new String(receivePacket.getData()) ) );
					//Assign ID and forward the message to the sequencer 
					//sendSequencerMessage(Configuration.FRONTEND_PORT_NUMBER, clientRequestMessage);
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

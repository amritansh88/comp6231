package drms.org.sequencer;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import drms.org.util.Configuration;

/**
 * The Sequencer initializes 
 * 	- Socket on which the sequencer listens messages from FrontEnd 
 * 	- FrontEndMessageListener, a listener that captures messages from FrontEnd.
 *  - FrontEndMessageListener stamps messages before sending messages to 
 * @author murindwaz
 */
public class SequencerDriver {

	public static void main(String args[]) throws SocketException, UnknownHostException{
		System.out.println("*** Sequencer is up and running = Requests will be forwarded to FrontEnd ***");
		DatagramSocket socket = new DatagramSocket( Configuration.SEQUENCER_PORT_NUMBER);
		SequencerImpl sequencerImpl = new SequencerImpl( socket );
		(new Thread( new FrontEndMessageListener(sequencerImpl, socket ))).start(); 
	}
}
package drms.org.sequencer;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import drms.org.frontend.ClientMessageListener;
import drms.org.frontend.SequencerMessageListener;
import drms.org.util.Configuration;


public class SequencerDriver extends SequencerImpl {
	



	SequencerDriver(DatagramSocket replicaSocket) throws UnknownHostException {
		super(replicaSocket);
	}
	
	public static void main(String args[]) throws SocketException, UnknownHostException{
		
		DatagramSocket frontEndToSequencerSocket = new DatagramSocket( Configuration.SEQUENCER_PORT_NUMBER);
		SequencerImpl sequencerImpl=new SequencerImpl( frontEndToSequencerSocket );
		System.out.println("**************SequencerDriver is up and Running*************");
		System.out.println("**************Data would be forwarded to Replica Managers*************");
		//sequencerImpl.receiveFrontEndMessage(udpFrontEndPortNumber);
		(new Thread( new FrontEndMessageListener(sequencerImpl, frontEndToSequencerSocket))).start(); 
	}
}

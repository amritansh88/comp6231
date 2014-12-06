package drms.org.frontend;

import java.net.DatagramSocket;

import drms.org.util.MessageListener;

public class SequencerMessageListener implements MessageListener {

	/**
	 * 
	 */
	private DatagramSocket socket;
	
	
	/**
	 * Takes the port number the sequencer communicates with the front end from
	 * @param fesSocket Front End Sequencer Socket 
	 */
	public SequencerMessageListener(DatagramSocket fesSocket) {
		socket	= fesSocket;
	}

	
	
	public SequencerMessageListener(FrontEndImpl frontEnd, DatagramSocket sequencerToFrontEndSocket) {
		// TODO Auto-generated constructor stub
	}



	@Override
	public void run() {
		onMessage();
	}

	@Override
	public void onMessage() {
		//@todo do something whenever there is a message coming in here 
	}

}

package drms.org.sequencer;

import drms.org.util.MessageListener;

public class ReplicaMessageListener implements MessageListener {

	@Override
	public void run() {
		onMessage();
	}

	@Override
	public void onMessage() {
		//@todo implement things to do when there is  amessage from the replica manager
	}

}

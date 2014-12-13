package drms.org.sequencer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import drms.org.model.NetworkMessage;
import drms.org.model.Traffic;
import drms.org.util.Configuration;
import drms.org.util.MessageListener;
import drms.org.util.NetworkMessageParser;
import drms.org.util.StringTransformer;

/**
 * The FrontEndMessage listener, listerns to message coming from the front end,
 * and forward them to Replica Managers.
 * @todo acknowledge the front end about that operation
 * @author murindwaz
 */
public class FrontEndMessageListener implements MessageListener {

	private SequencerImpl sequencer;
	private DatagramSocket socket;

	public FrontEndMessageListener(SequencerImpl sequencerImpl, DatagramSocket frontEndToSequencerSocket) {
		sequencer = sequencerImpl;
		socket = frontEndToSequencerSocket;
	}

	@Override
	public void run() {
		onMessage();
	}

	@Override
	public void onMessage() {
		while (true) {
			System.out.println("Waiting for datagram packet from FrontEnd");
			DatagramPacket request = new DatagramPacket(new byte[Configuration.BUFFER_SIZE],
					new byte[Configuration.BUFFER_SIZE].length);
			DatagramSocket lsocket = null;
			try {
				socket.receive(request);
				Map<String, InetAddress> nodes = sequencer.getClusterNodes();
				int uniqueID = sequencer.getUniqueId();
				Traffic traffic = new Traffic(); 
				traffic.setMessageId( String.valueOf(uniqueID) );
				lsocket = new DatagramSocket();
				for (Entry<String, InetAddress> node : nodes.entrySet()) {
					if (!sequencer.getTraffic().containsKey(String.valueOf(uniqueID))) {
						sequencer.getTraffic().put(String.valueOf(uniqueID), new HashMap<String, NetworkMessage>());
					}
					sequencer.getTraffic().get(String.valueOf(uniqueID))
							.put(node.getKey(), NetworkMessageParser.parse(new String(request.getData())));
					lsocket.send(new DatagramPacket(request.getData(), request.getData().length, node
							.getValue(), Configuration.RM_PORT_NUMBER));
					traffic.getReplicaManagers().add(node.getKey());
				}
				
				//ClientMessageListener with MessageId, and List of RM to wait from 
				NetworkMessage networkMessage = new NetworkMessage("", "", StringTransformer.getString(traffic));
				byte[] acknowledgment = StringTransformer.getString(networkMessage).getBytes();
				lsocket.send(new DatagramPacket(acknowledgment, acknowledgment.length, request.getAddress(), request.getPort()));
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if( lsocket != null && !lsocket.isClosed()) { 
					lsocket.close();
				}
			}
		}
	}
}

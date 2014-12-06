/**
 * @author Amritansh
 * @Description Final Project : Software Failure Tolerant and/or Highly Available Distributed Reservation Management System
 * Sequencer Interface Implementation 
 * 
 */
package drms.org.sequencer;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import drms.org.model.NetworkMessage;
import drms.org.util.Configuration;

public class SequencerImpl implements SequencerInterface {

	
	
	/**
	 * @todo change the ID on this section, and replace it with timestamp at computer's clock
	 * @deprecated the unique ID will be replaced with a timestamp. 
	 * 
	 */
	static int uniqueID = 0;
	
	/**
	 * @deprecated use the constant variable offered by 
	 */
	final static int udpFrontEndPortNumber = 223;
	/** replica manager port number **/
	final static int REMOTE_PORT_NUMBER = 2626;
	final static int MULTICAST_PORT_NUMBER = 3456;

	static InetAddress IPAddress;
	static InetAddress RemoteIPAddress;
	/**
	 * Map of nodes to send messages to, and listen to
	 */
	private Map<String, InetAddress> nodes;
	
	
	/**
	 * This section will keep track of sent, and received messages. 
	 * <code> 
	 * 		Map<String, Map<String, NetworkMessage>> 
	 *</code>
	 */
	private Map<String, Map<String, NetworkMessage>> traffic;

	
	
	/**
	 * @param replicaSocket
	 * @throws UnknownHostException
	 */
	SequencerImpl(DatagramSocket replicaSocket) throws UnknownHostException {
		IPAddress = InetAddress.getLocalHost();
		RemoteIPAddress = InetAddress.getByName(Configuration.RM_ONE);//
		nodes = new HashMap<String, InetAddress>();
		for (String node : Configuration.CLUSTER) {
			nodes.put(node, InetAddress.getByName(node));
		}
		//
		traffic = new HashMap<String, Map<String, NetworkMessage>>();
	}

	/**
	 * Used by processes that whish to access and update outgoing/incoming messages 
	 * @return traffic
	 */
	public Map<String, Map<String, NetworkMessage>> getTraffic() {
		return traffic;
	}

	/**
	 * Used by processes that wish to access usable nodes
	 * @return nodes 
	 */
	public Map<String, InetAddress> getClusterNodes() {
		return nodes;
	}

	

	/**
	 * @todo test and replace this method, if not performing well.
	 * @return unique id 
	 */
	public int getUniqueId() {
		return uniqueID + 1;
	}

}
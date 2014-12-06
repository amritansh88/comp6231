package drms.org.util;

import drms.org.model.NetworkMessage;

public class NetworkMessageParser {
	
	
	/**
	 * This utility library will help to parse message back and forth for the Sequencer and Front end to add extra information 
	 * @param data
	 * @return networkMessage
	 */
	public static NetworkMessage parse(String data){
		return new NetworkMessage("", "", "");
	}
}

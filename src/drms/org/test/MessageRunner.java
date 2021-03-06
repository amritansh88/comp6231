package drms.org.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import drms.org.model.*;
import drms.org.model.NetworkMessage;
import drms.org.util.*;


public class MessageRunner {


	
	
		
	
	private static DatagramSocket sequencerSocket;
	

	public static void main(String[] args) throws IOException {
		
		byte[] bdata = new byte[102400];
		sequencerSocket = new DatagramSocket();
		
		//@todo create this message in client 
		String data = StringTransformer.getString(new Account("Pa", "Man", "pmn@email.ru", "514571", "pmn4", "pmn4", Configuration.DAWSON));
		
		NetworkMessage networkMessage = new NetworkMessage(Configuration.DAWSON, Configuration.ACCOUNT_OPERATION, data);
				bdata = (StringTransformer.getString(networkMessage)).getBytes();
		System.out.println("Size of data to be sent :" + data.length() + " bytes");
		System.out.print("Sequencer: Ready to send data " + "\n");
		
		DatagramPacket request = new DatagramPacket(bdata, bdata.length, InetAddress.getByName(Configuration.RM_ONE), Configuration.RM_PORT_NUMBER);
		sequencerSocket.send(request);
		//timesout after 1 minute
		sequencerSocket.setSoTimeout(10000000);
		
		//waiting for the answer from the server
		DatagramPacket response = new DatagramPacket( new byte[Configuration.BUFFER_SIZE], Configuration.BUFFER_SIZE, InetAddress.getByName(Configuration.RM_ONE), Configuration.RM_PORT_NUMBER);
		sequencerSocket.receive(response);
		System.out.println( new String(response.getData()) );
	}

}

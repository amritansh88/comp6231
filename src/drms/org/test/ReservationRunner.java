package drms.org.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import drms.org.model.*;
import drms.org.model.NetworkMessage;
import drms.org.util.*;


public class ReservationRunner {


	
	
		
	
	private static DatagramSocket sequencerSocket;
	

	public static void main(String[] args) throws IOException {
		
		byte[] bdata = new byte[102400];
		sequencerSocket = new DatagramSocket();
		
		//@todo create this message in client 
		String data = StringTransformer.getString(new Reservation("pmn4", "pmn4", "The Tragedy of Hamlet, Prince of Denmark", "William Shakespeare", Configuration.DAWSON));
		NetworkMessage networkMessage = new NetworkMessage(Configuration.DAWSON, Configuration.RESERVATION_OPERATION, data);
				bdata = (StringTransformer.getString(networkMessage)).getBytes();
		System.out.println("Size of data to be sent :" + data.length() + " bytes");
		System.out.print("Sequencer: Ready to send data " + "\n");
		DatagramPacket request = new DatagramPacket(bdata, bdata.length, InetAddress.getByName(Configuration.RM_ONE), Configuration.REPLICA_MANAGER_SOCKET);
		sequencerSocket.send(request);
		//timesout after 1 minute
		sequencerSocket.setSoTimeout(10000000);
		
		//waiting for the answer from the server
		DatagramPacket response = new DatagramPacket( new byte[Configuration.BUFFER_SIZE], Configuration.BUFFER_SIZE, InetAddress.getByName(Configuration.RM_ONE), Configuration.REPLICA_MANAGER_SOCKET);
		sequencerSocket.receive(response);
		System.out.println( new String(response.getData()) );
	}

}

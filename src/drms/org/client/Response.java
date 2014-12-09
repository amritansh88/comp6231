package drms.org.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import drms.org.util.Configuration;



/**
 * This class will wait for the response coming from FrontEnd and display the content 
 */
public class Response implements Runnable {
	
	
	private DatagramSocket socket;
	Response(DatagramSocket datagramSocket ){
		socket = datagramSocket;
	}
	
	
	@Override
	public void run() {
		while( true ){
			DatagramPacket response = new DatagramPacket( new byte[Configuration.BUFFER_SIZE], Configuration.BUFFER_SIZE);
			try {
				socket.receive(response);
				Configuration.showWelcomeMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

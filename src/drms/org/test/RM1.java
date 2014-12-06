package drms.org.test;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class RM1 {
	public void receiveSequencerMessage(){
		try{
			InetAddress group=InetAddress.getByName("225.1.1.1");
			MulticastSocket multicastSock= new MulticastSocket(3456);
			multicastSock.joinGroup(group);
			byte[] buffer=new byte[1002400];
			DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
			multicastSock.receive(packet);
			System.out.println(new String(buffer));
			multicastSock.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		RM1 mr=new RM1();
		mr.receiveSequencerMessage();
	}}

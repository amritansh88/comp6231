package drms.org.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author murindwaz This utility library is inspired from source code located
 *         at the next link
 * @link 
 *       http://stackoverflow.com/questions/2037717/how-to-use-multicast-on-a-multi
 *       -homed-system-java-linux
 */
public class MultihomeUtil {
	// SDP constants
	public static final String MULTICAST_ADDRESS = "239.255.255.250";
	public static final int MULTICAST_PORT = 1900;

	// args: each arg is the name of an interface.
	public void doMain(Set<String> args) throws Exception {

		InetSocketAddress socketAddress = new InetSocketAddress(MULTICAST_ADDRESS, MULTICAST_PORT);
		MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
		Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();

		while (ifs.hasMoreElements()) {
			NetworkInterface xface = ifs.nextElement();
			Enumeration<InetAddress> addrs = xface.getInetAddresses();
			String name = xface.getName();
			while (addrs.hasMoreElements()) {
				InetAddress addr = addrs.nextElement();
				System.out.println(name + " ... has addr " + addr);
			}

			if (args.contains(name)) {
				System.out.println("Adding " + name + " to our interface set");
				socket.joinGroup(socketAddress, xface);
			}
		}

		byte[] buffer = new byte[1500];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

		while (true) {
			try {
				packet.setData(buffer, 0, buffer.length);
				socket.receive(packet);
				System.out.println("Received pkt from " + packet.getAddress() + " of length " + packet.getLength());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Set<String> argSet = new HashSet<String>();
		MultihomeUtil multi = new MultihomeUtil();

		for (String arg : args) {
			argSet.add(arg);
		}

		multi.doMain(argSet);
	}
}
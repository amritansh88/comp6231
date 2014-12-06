package drms.org.util;



/**
 * Message Listener is a common interface for point-to-point communication  
 * @author murindwaz
 */
public interface MessageListener extends Runnable {
	void onMessage();
}
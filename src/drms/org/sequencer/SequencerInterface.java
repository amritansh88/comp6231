/**
 * @author Amritansh
 * @Description Final Project : Software Failure Tolerant and/or Highly Available Distributed Reservation Management System
 * Sequencer Interface defines the functions performed to be implemented by the Sequencer
 * 
 */
package drms.org.sequencer;

public interface SequencerInterface {
	
	public String receiveFrontEndMessage(int portNumber);
	
	public String addMessageID(String clientMessage);
	
	public void sendMulticastMessage( String data);
	
}

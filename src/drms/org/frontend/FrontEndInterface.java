/**
 * @author Amritansh
 * @Description Final Project : Software Failure Tolerant and/or Highly Available Distributed Reservation Management System
 * FrontEnd Interface defines the functions performed to be implemented by the FrontEnd
 * 
 */
package drms.org.frontend;

public interface FrontEndInterface {
	
	public String validateServerResponse(String response1,String response2,String response3);
	
	public String receiveServerResponse();
	
	public void sendClientResponse(int port,String data);
	
	public String receiveClientMessage(int portNumber);
	
	public void UpdateMessageMap(int key, String value);
	
	public String sendSequencerMessage(int portNumber,  String data);
}

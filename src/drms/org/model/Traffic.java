package drms.org.model;

import java.util.ArrayList;
import java.util.List;



/**
 * This datatype will be sent as a Acknowledgment from FE Message listener to  
 * @author murindwaz
 */
public class Traffic {
	private String messageId; 
	private List<String> replicaManagers;
	
	
	public Traffic() {
		replicaManagers = new ArrayList<String>();
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public List<String> getReplicaManagers() {
		return replicaManagers;
	}
	public void setReplicaManagers(List<String> replicaManagers) {
		this.replicaManagers = replicaManagers;
	}
}

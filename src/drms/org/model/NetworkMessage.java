package drms.org.model;

/**
 * Template of a message to be sent over the network
 * 
 * @author murindwaz
 */
public class NetworkMessage {

	private String destination;
	private String operation;
	private String payload;

	// server sent variables
	private String id;
	private String replica;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReplica() {
		return replica;
	}

	public void setReplica(String replica) {
		this.replica = replica;
	}

	public NetworkMessage(String destination, String operation, String payload){
		this(destination, operation, payload, "-", "-");
	}

	public NetworkMessage(String destination, String operation, String payload, String id, String replica) {
		this.destination = destination;
		this.operation = operation;
		this.payload = payload;
		this.id = id;
		this.replica = replica;
	}


}

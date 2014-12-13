package drms.org.util;

import java.io.StringReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import drms.org.model.*;





public class NetworkMessageParser {
	/**
	 * This utility library will help to parse message back and forth for the
	 * Sequencer and Front end to add extra information
	 * @param data
	 * @return networkMessage
	 */
	public static NetworkMessage parse(String argument) {
		Gson gson = new Gson();
		//NetworkMessage nmsg = gson.fromJson(argument, NetworkMessage.class);
		JsonReader reader = new JsonReader(new StringReader(argument.trim()));
		JsonObject json = gson.fromJson(reader, JsonObject.class);
		reader.setLenient(true);
		NetworkMessage networkMessage = new NetworkMessage(json.getAsJsonPrimitive("destination").getAsString(), json
				.getAsJsonPrimitive("operation").getAsString(), json.getAsJsonPrimitive("payload").getAsString());
		if (json.has("id")) {
			networkMessage.setId(json.getAsJsonPrimitive("id").getAsString());
		}
		if (json.has("replica")) {
			networkMessage.setReplica(json.getAsJsonPrimitive("replica").getAsString());
		}
		
		return networkMessage;
	}

	/**
	 * @param networkMessage
	 * @return
	 */
	public static Account parseAccount(NetworkMessage networkMessage) {
		Gson gson = new Gson();
		Account account = new Account();
		JsonReader reader = new JsonReader(new StringReader(networkMessage.getPayload().trim()));
		JsonObject json = gson.fromJson(reader, JsonObject.class);
		reader.setLenient(true);
		account.setPassword(json.getAsJsonPrimitive("password").getAsString());
		account.setUsername(json.getAsJsonPrimitive("username").getAsString());
		account.setAdmin(json.getAsJsonPrimitive("admin").getAsBoolean());
		try {
			account.setFirst(json.getAsJsonPrimitive("first").getAsString());
			account.setLast(json.getAsJsonPrimitive("last").getAsString());
			account.setEmail(json.getAsJsonPrimitive("email").getAsString());
			account.setTelephone(json.getAsJsonPrimitive("telephone").getAsString());
		} catch (Exception e) {
			System.out.println(String.format("An error occurred :: ", e.getMessage()));

		}
		return account;
	}

	/**
	 * @param networkMessage
	 * @return
	 */
	public static Book parseBook(NetworkMessage networkMessage) {
		Gson gson = new Gson();
		Book book = new Book();
		JsonReader reader = new JsonReader(new StringReader(networkMessage.getPayload().trim()));
		JsonObject json = gson.fromJson(reader, JsonObject.class);
		reader.setLenient(true);
		book.setAuthor(json.getAsJsonPrimitive("author").getAsString());
		book.setTitle(json.getAsJsonPrimitive("title").getAsString());
		try {
			book.setLibrary(json.getAsJsonPrimitive("library").getAsString());
			book.setReserved(json.getAsJsonPrimitive("reserved").getAsBoolean());
		} catch (Exception e) {
			System.out.println(String.format("An error occurred :: ", e.getMessage()));
		}
		return book;
	}

	/**
	 * @param networkMessage
	 * @return
	 */
	public static Reservation parseReservation(NetworkMessage networkMessage) {
		Gson gson = new Gson();
		Book book = new Book();
		Account account = new Account();
		Reservation reservation = new Reservation();
		JsonReader reader = new JsonReader(new StringReader(networkMessage.getPayload().trim()));
		JsonObject json = gson.fromJson(reader, JsonObject.class);
		if(json.has("book")){
			book = parseBook(new NetworkMessage("", "", json.getAsJsonPrimitive("book").getAsString()));
		}
		if(json.has("account")){
			account = parseAccount(new NetworkMessage("", "", json.getAsJsonPrimitive("account").getAsString()));
		}
		reservation.setAccount(account);
		reservation.setBook(book);
		return reservation;
	}
	
	
	
	
	public static Traffic parseTraffic( NetworkMessage networkMessage ){
		Traffic traffic = (new Gson()).fromJson(networkMessage.getPayload(), Traffic.class);
		return traffic;
	}
}

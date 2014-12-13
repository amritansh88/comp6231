package drms.org.util;


import com.google.gson.Gson;

import drms.org.model.*;




/**
 * This utility will take an object and transform it into a valid JSON String 
 * @author murindwaz
 */
public class StringTransformer {

	
	/**
	 * Helper that transforms an Account into a JSON string
	 * @param Account account
	 * @return String 
	 */
	public static String getString(Account account ){
		return (new Gson()).toJson(account);
	}
	

	/**
	 * Helper that transforms a NetworkMessage into a JSON String
	 * @param networkMessage
	 * @return
	 */
	public static String getString(NetworkMessage networkMessage){
		return (new Gson()).toJson(networkMessage);
	}

	/**
	 * @param book
	 * @return
	 */
	public static String getString(Book book){
		return (new Gson()).toJson(book);
	}

	/**
	 * @param reservation
	 * @return
	 */
	public static String getString(Reservation reservation){
		return (new Gson()).toJson(reservation);
	}


	public static String getString(Traffic traffic) {
		return (new Gson()).toJson(traffic);
	}
	
}
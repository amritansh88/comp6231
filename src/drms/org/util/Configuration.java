package drms.org.util;

import java.net.SocketAddress;






/**
 * This configuration file will be used to hold some shared variables 
 */
public class Configuration {

	
	public static final int RM_PORT_NUMBER = 2626; 
	public static final int FRONTEND_PORT_NUMBER = 2233; 
	public static final int SEQUENCER_TO_FRONTEND_PORT_NUMBER = 2244;
	public static final int SEQUENCER_PORT_NUMBER = 1098;
	/**
	 * @deprecated has to be replaced with RM_PORT_NUMBER instead
	 */
	public static final int REPLICA_MANAGER_SOCKET = 2626;
	public static final String RM_ONE = "Amritansh-HP";
	public static final String RM_TWO = "gost-via-mac";
	public static final String RM_THREE = "";
	//this may not be used all the time
	public static final String RM_FOUR = "";
	
	
	//Institutions
	public static final String CONCORDIA = "concordia";
	public static final String MCGILL = "mcgill";
	public static final String DAWSON = "dawson";
	
	
	//Operations 
	public static final String  ACCOUNT_OPERATION = "account";
	public static final String  OVERDUE_OPERATION = "overdue";
	public static final String  RESERVATION_OPERATION = "reservation";
	
	
	public static final int BUFFER_SIZE = 2048;
	public static final String CLUSTER[] = new String[]{ RM_ONE, RM_TWO, RM_THREE, RM_FOUR};
	
	

	
	/**
	 * Description: Welcome menu displayed in general
	 */
	public static void showWelcomeMenu() {

		System.out.println("\n****Welcome to DRMS Client ****\n");
		System.out.println("Please select an option (1-4)");
		System.out.println("1. Account");
		System.out.println("2. OverDue");
		System.out.println("3. Reservation");
		System.out.println("4. Exit");
	}
	
	
}
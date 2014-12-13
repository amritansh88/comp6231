package drms.org.util;

import java.net.SocketAddress;


/**
 * This configuration file will be used to hold some shared variables 
 */
public class Configuration {

	
	public static final int RM_PORT_NUMBER = 2626; 
	public static final int FRONTEND_PORT_NUMBER = 2233; 
	public static final int SEQUENCER_TO_FRONTEND_PORT_NUMBER = 2244;
	public static final int RM_TO_FRONTEND_PORT_NUMBER = 2255;
	public static final int SEQUENCER_PORT_NUMBER = 1098;
	//port to which send/receive will be done on client side 
	public static final int CLIENT_PORT_NUMBER = 1099;
	/**
	 * @deprecated has to be replaced with RM_PORT_NUMBER instead
	 */
	public static final int REPLICA_MANAGER_SOCKET = 2626;
	public static final String RM_ONE = "172.31.93.79";//Amritansh-HP
	public static final String RM_TWO = "gost-via-mac";// 172.31.36.174
	public static final String RM_THREE = "132.205.64.136";//Amritansh - Lab computer
	//this may not be used all the time
	public static final String RM_FOUR = "";
	
	
	//Institutions
	public static final String CONCORDIA = "concordia";
	public static final String MCGILL = "mcgill";
	public static final String DAWSON = "dawson";
	public static final String[] ALLOWED_INSTITUTIONS = new String[]{CONCORDIA, MCGILL, DAWSON};
	
	//Operations 
	public static final String  ACKNOWLEDGMENT_OPERATION = "ack";
	public static final String  ACCOUNT_OPERATION = "account";
	public static final String  OVERDUE_OPERATION = "overdue";
	public static final String  RESERVATION_OPERATION = "reservation";
	
	
	public static final int BUFFER_SIZE = 2048;
	public static final String CLUSTER[] = new String[]{ RM_ONE, RM_TWO, RM_THREE, RM_FOUR};
	
	
	
	/**
	 * Variables holding messages that will be displayed during student/administration 
	 * interaction with the library servers
	 */
	public static final String _REQUEST_FIRST_NAME = "Enter your first name";
	public static final String _REQUEST_LAST_NAME = "Enter your last name";
	public static final String _REQUEST_PHONE_NUMBER = "Enter your phone number";
	public static final String _REQUEST_USER_NAME = "Create a username(Maximum:15 characters & Minimum:6 characters)";
	public static final String _REQUEST_PASSWORD = "Create a password(Minimum: 6 characters)";
	public static final String _REQUEST_EDUCATIONAL_INSTITUTION = "Enter the name of your instituion";
	public static final String _REQUEST_EMAIL_ADDRESS = "Enter your email address";
	public static final String _REQUEST_BOOKNAME = "Enter the name of the book to be reserved";
	public static final String _REQUEST_USERNAME = "Enter username";
	public static final String _REQUEST_PASS = "Enter your password";
	public static final String _REQUEST_BOOKLOAN_PERIOD = "Enter the loan period";
	

	
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
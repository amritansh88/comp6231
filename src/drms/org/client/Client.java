/**
 * @author Amritansh
 * @Description Final Project : Software Failure Tolerant and/or Highly Available Distributed Reservation Management System
 * Client communicates with the front end and sends the message to the front end in the form of a string using UDP messages. 
 * 
 */
package drms.org.client;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.log4j.Logger;

import drms.org.model.Account;
import drms.org.model.NetworkMessage;
import drms.org.model.Reservation;
import drms.org.util.Configuration;
import drms.org.util.StringTransformer;




/**
 * @todo remove all _response = "" and listen to whatever comes later... 
 */
public class Client {

	static final Logger log = Logger.getLogger(Client.class);
	// Start of main function
	public static void main(String args[]) throws SocketException {
		/**
		 * Variables to store menu options from student/administrator as input from command line
		 */
		int _userChoice = 0;
		final DatagramSocket socket = new DatagramSocket(Configuration.CLIENT_PORT_NUMBER);
		// ClientProcess Reference for sending messages from Client
		Request request = new Request(socket);
		//This thread will wait for responses from FrontEnd 

		/**
		 * Variable reference to obtain user input
		 */
		Scanner _keyboardInputGeneralMenu = new Scanner(System.in);
		/** 
		 * Variables to store values from student/administrator as input from command line. 
		 */
		String _userInputEducationalInstitution = "";
		String _adminInputEducationalInstitution = "";
		String _adminUsername;
		String _adminPassword;
		String _userInputFirstName = "";
		String _userInputLastName = "";
		String _userInputPhoneNumber = "";
		String _userInputUserName = "";
		String _userInputPassword = "";
		String _userInputEmailAddress = "";
		String _userInputbookName = "";
		String _authorName = "";
		int _numDays = 0;
		
		
		String destination = null;
		String payload = null;
		Configuration.showWelcomeMenu();

		try {
			while (true) {
				Boolean _valid = false;
				// Enforces a valid integer input.
				while (!_valid) {
					try {
						_userChoice = _keyboardInputGeneralMenu.nextInt();
						_valid = true;
					} catch (Exception e) {
						log.info("Invalid Input, please enter an Integer");
						_valid = false;
						_keyboardInputGeneralMenu.nextLine();
					}
				}
				// Manage user selection.
				switch (_userChoice) {
				// Logic for account creation
				case 1:
					System.out.println("Create your account. Please enter the following details >>>>>>>>");
					System.out.println(Configuration._REQUEST_EDUCATIONAL_INSTITUTION);
					_userInputEducationalInstitution = _keyboardInputGeneralMenu.next();
					while (Arrays.asList(Configuration.ALLOWED_INSTITUTIONS).indexOf(
							_userInputEducationalInstitution.toLowerCase()) < 0) {
						log.info("Please choose from the following instiutions : concordia/mcgill/dawson");
						_userInputEducationalInstitution = _keyboardInputGeneralMenu.next().trim();
						if (Arrays.asList(Configuration.ALLOWED_INSTITUTIONS).indexOf(
								_userInputEducationalInstitution.toLowerCase()) >= 0) {
							break;
						}
					}
					System.out.println(Configuration._REQUEST_FIRST_NAME);
					_userInputFirstName = _keyboardInputGeneralMenu.next();
					System.out.println(Configuration._REQUEST_LAST_NAME);
					_userInputLastName = _keyboardInputGeneralMenu.next();
					System.out.println(Configuration._REQUEST_EMAIL_ADDRESS);
					_userInputEmailAddress = _keyboardInputGeneralMenu.next();
					System.out.println(Configuration._REQUEST_PHONE_NUMBER);
					_userInputPhoneNumber = _keyboardInputGeneralMenu.next();
					System.out.println(Configuration._REQUEST_USER_NAME);
					_userInputUserName = _keyboardInputGeneralMenu.next().trim();
					int userNamelength = _userInputUserName.length();
					while (userNamelength < 6 || userNamelength > 15) {
						log.info("Username min:6 characters and max:15 characters.....Try Again");
						System.out.println(Configuration._REQUEST_USER_NAME);
						_userInputUserName = _keyboardInputGeneralMenu.next().trim();
						userNamelength = _userInputUserName.length();
						if (userNamelength < 6 || userNamelength > 15) {
							continue;
						} else {
							break;
						}
					}
					System.out.println(Configuration._REQUEST_PASSWORD);
					_userInputPassword = _keyboardInputGeneralMenu.next().trim();
					int userPassLength = _userInputPassword.length();
					while (userPassLength < 6) {
						log.info("Password cannot be less than 6 characters.....Try Again");
						System.out.println(Configuration._REQUEST_PASSWORD);
						_userInputPassword = _keyboardInputGeneralMenu.next().trim();
						userPassLength = _userInputPassword.length();
						if (userPassLength < 6) {
							continue;
						} else {
							break;
						}
					}
					System.out.println("\n Waiting................");
					destination = _userInputEducationalInstitution;
					payload = StringTransformer.getString(new Account(_userInputFirstName, _userInputLastName,
							_userInputEmailAddress, _userInputPhoneNumber, _userInputUserName, _userInputPassword,
							_userInputEducationalInstitution));
					request.send(StringTransformer.getString(new NetworkMessage(destination,Configuration.ACCOUNT_OPERATION, payload)));
					Configuration.showWelcomeMenu();
				break;

				// Logic for overDue
				case 2:
					System.out.println("Please enter the below details to get a list of book non returners >>>>>>>>");
					Scanner adminInputD = new Scanner(System.in);
					System.out.println(Configuration._REQUEST_USERNAME);
					_adminUsername = adminInputD.nextLine();
					System.out.println(Configuration._REQUEST_PASS);
					_adminPassword = adminInputD.nextLine();
					System.out.println(Configuration._REQUEST_EDUCATIONAL_INSTITUTION);
					_adminInputEducationalInstitution = adminInputD.next().trim();
					while (Arrays.asList(Configuration.ALLOWED_INSTITUTIONS).indexOf(
							_userInputEducationalInstitution.toLowerCase()) < 0) {
						log.info("Please choose from the following instiutions : concordia/mcgill/dawson");
						_adminInputEducationalInstitution = _keyboardInputGeneralMenu.next().trim();
						if (Arrays.asList(Configuration.ALLOWED_INSTITUTIONS).indexOf(
								_userInputEducationalInstitution.toLowerCase()) >= 0) {
							break;
						}
					}
					System.out.println(Configuration._REQUEST_BOOKLOAN_PERIOD);
					Boolean _loanPeriodCheck = false;
					// Enforces a _valid integer input.
					while (!_loanPeriodCheck) {
						try {
							_numDays = adminInputD.nextInt();
							_loanPeriodCheck = true;
						} catch (Exception e) {
							log.info("Invalid Input, please enter an Integer");
							_loanPeriodCheck = false;
							adminInputD.nextInt();
						}
					}
					System.out.println("\n Waiting................");
					destination = _adminInputEducationalInstitution;
					Reservation overdue = new Reservation();
					overdue.getAccount().setAdmin(true);
					overdue.getAccount().setUsername(_adminUsername);
					overdue.getAccount().setPassword(_adminPassword);
					overdue.getAccount().setInstitution(_adminInputEducationalInstitution);
					overdue.setDays(_numDays);
					payload = StringTransformer.getString(overdue);
					request.send(StringTransformer.getString(new NetworkMessage(_adminInputEducationalInstitution, Configuration.OVERDUE_OPERATION, payload)));
					Configuration.showWelcomeMenu();
					break;

				// Logic for book reservation
				case 3:
					System.out.println("Please enter the details below to reserve a book >>>>>>>");
					Scanner _bookReservationInput = new Scanner(System.in);
					System.out.println(Configuration._REQUEST_EDUCATIONAL_INSTITUTION);
					_adminInputEducationalInstitution = _bookReservationInput.next().trim();
					while (Arrays.asList(Configuration.ALLOWED_INSTITUTIONS).indexOf(
							_adminInputEducationalInstitution.toLowerCase()) < 0) {
						log.info("Please choose from the following instiutions : concordia/mcgill/dawson");
						_adminInputEducationalInstitution = _keyboardInputGeneralMenu.next().trim();
						if (Arrays.asList(Configuration.ALLOWED_INSTITUTIONS).indexOf(
								_adminInputEducationalInstitution.toLowerCase()) >= 0) {
							break;
						}
					}
					System.out.println("Enter Username");
					_userInputUserName = _bookReservationInput.next().trim();
					System.out.println("Enter Password");
					_userInputPassword = _bookReservationInput.next().trim();
					System.out.println(Configuration._REQUEST_BOOKNAME);
					_userInputbookName = _bookReservationInput.next();
					System.out.println("Enter author name");
					_authorName = _bookReservationInput.next();
					System.out.println("\n Waiting................");
					destination = _adminInputEducationalInstitution;
					payload = StringTransformer.getString(new Reservation(_userInputUserName, _userInputPassword,
							_userInputbookName, _authorName, _adminInputEducationalInstitution));
					request.send(StringTransformer.getString(new NetworkMessage(destination,Configuration.RESERVATION_OPERATION, payload)));
					Configuration.showWelcomeMenu();
				break;
				case 4:
					log.info("You chose to exit the application. Have a nice day !");
					_keyboardInputGeneralMenu.close();
					System.exit(0);
				default:
					log.info("Invalid Input, please try again.");
				break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}

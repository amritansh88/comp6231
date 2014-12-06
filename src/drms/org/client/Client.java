/**
 * @author Amritansh
 * @Description Final Project : Software Failure Tolerant and/or Highly Available Distributed Reservation Management System
 * Client communicates with the front end and sends the message to the front end in the form of a string using UDP messages. 
 * 
 */
package drms.org.client;

import java.util.Scanner;

import org.apache.log4j.Logger;

import drms.org.model.Account;
import drms.org.model.NetworkMessage;
import drms.org.model.Reservation;
import drms.org.util.Configuration;
import drms.org.util.StringTransformer;

public class Client {

	
	
	
	
	static final Logger log = Logger.getLogger(Client.class);
	// Start of main function
	public static void main(String args[]) {
		// Variables to store menu options from student/administrator as input
		// from command line
		int _userChoice = 0;
		// Variable reference to obtain user input
		Scanner _keyboardInputGeneralMenu = new Scanner(System.in);
		// Variables to store values from student/administrator as input from
		// command line.
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

		// Variables holding messages that will be displayed during
		// student/administration interaction with the library servers
		String _requestFirstName = "Enter your first name";
		String _requestLastName = "Enter your last name";
		String _requestPhoneNumber = "Enter your phone number";
		String _requestUserName = "Create a username(Maximum:15 characters & Minimum:6 characters)";
		String _requestPassword = "Create a password(Minimum: 6 characters)";
		String _requestEducationalInstitution = "Enter the name of your instituion";
		String _requestEmailAddress = "Enter your email address";
		String _requestBookName = "Enter the name of the book to be reserved";
		String _requestUsername = "Enter username";
		String _requestPass = "Enter your password";
		String _requestBookLoanPeriod = "Enter the loan period";

		// ClientProcess Reference for sending messages from Client
		ClientProcess clientProcess = new ClientProcess();
		String _response = null;
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
					System.out.println(_requestEducationalInstitution);
					_userInputEducationalInstitution = _keyboardInputGeneralMenu.next();
					while (!(_userInputEducationalInstitution.toLowerCase().equals("concordia")
							|| _userInputEducationalInstitution.toLowerCase().equals("mcgill") || _userInputEducationalInstitution
							.toLowerCase().equals("dawson"))) {

						log.info("Please choose from the following instiutions : concordia/mcgill/dawson");
						_userInputEducationalInstitution = _keyboardInputGeneralMenu.next().trim();
						if (!"concordia".equals(_userInputEducationalInstitution.toLowerCase())
								|| !"mcgill".equals(_userInputEducationalInstitution.toLowerCase())
								|| !"dawson".equals(_userInputEducationalInstitution.toLowerCase())) {
							continue;
						} else
							break;
					}
					System.out.println(_requestFirstName);
					_userInputFirstName = _keyboardInputGeneralMenu.next();
					System.out.println(_requestLastName);
					_userInputLastName = _keyboardInputGeneralMenu.next();
					System.out.println(_requestEmailAddress);
					_userInputEmailAddress = _keyboardInputGeneralMenu.next();
					System.out.println(_requestPhoneNumber);
					_userInputPhoneNumber = _keyboardInputGeneralMenu.next();
					System.out.println(_requestUserName);
					_userInputUserName = _keyboardInputGeneralMenu.next().trim();
					int userNamelength = _userInputUserName.length();
					while (userNamelength < 6 || userNamelength > 15) {
						log.info("Username min:6 characters and max:15 characters.....Try Again");
						System.out.println(_requestUserName);
						_userInputUserName = _keyboardInputGeneralMenu.next().trim();
						userNamelength = _userInputUserName.length();
						if (userNamelength < 6 || userNamelength > 15) {
							continue;
						} else {
							break;
						}
					}
					System.out.println(_requestPassword);
					_userInputPassword = _keyboardInputGeneralMenu.next().trim();
					int userPassLength = _userInputPassword.length();
					while (userPassLength < 6) {
						log.info("Password cannot be less than 6 characters.....Try Again");
						System.out.println(_requestPassword);
						_userInputPassword = _keyboardInputGeneralMenu.next().trim();
						userPassLength = _userInputPassword.length();
						if (userPassLength < 6)
							continue;
						else
							break;
					}
					System.out.println("\n");
					System.out.println("Waiting................");
					destination = _userInputEducationalInstitution;
					payload = StringTransformer.getString(new Account(_userInputFirstName, _userInputLastName, _userInputEmailAddress, _userInputPhoneNumber, _userInputUserName, _userInputPassword, _userInputEducationalInstitution));
					_response = clientProcess.sendData( StringTransformer.getString( new NetworkMessage(destination, Configuration.ACCOUNT_OPERATION, payload)));
					log.info(_response);
					Configuration.showWelcomeMenu();
					break;

				// Logic for overDue
				case 2:
					System.out.println("Please enter the below details to get a list of book non returners >>>>>>>>");
					Scanner adminInputD = new Scanner(System.in);
					System.out.println(_requestUsername);
					_adminUsername = adminInputD.nextLine();
					System.out.println(_requestPass);
					_adminPassword = adminInputD.nextLine();
					System.out.println(_requestEducationalInstitution);
					_adminInputEducationalInstitution = adminInputD.next().trim();
					while (!(_adminInputEducationalInstitution.toLowerCase().equals("concordia")
							|| _adminInputEducationalInstitution.toLowerCase().equals("mcgill") || _adminInputEducationalInstitution
							.toLowerCase().equals("dawson"))) {

						log.info("Please choose from the following instiutions : concordia/mcgill/dawson");
						_adminInputEducationalInstitution = _keyboardInputGeneralMenu.next().trim();
						if (!"concordia".equals(_adminInputEducationalInstitution.toLowerCase())
								|| !"mcgill".equals(_adminInputEducationalInstitution.toLowerCase())
								|| !"dawson".equals(_adminInputEducationalInstitution.toLowerCase())) {
							continue;
						} else
							break;
					}
					System.out.println(_requestBookLoanPeriod);
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
					System.out.println("\n");
					System.out.println("Waiting................");
					destination = _adminInputEducationalInstitution;
					Reservation overdue = new Reservation();
						overdue.getAccount().setAdmin(true);
						overdue.getAccount().setUsername(_adminUsername);
						overdue.getAccount().setPassword(_adminPassword);
						overdue.getAccount().setInstitution(_adminInputEducationalInstitution);
						overdue.setDays(_numDays);
					 payload = StringTransformer.getString(overdue);
					 _response = clientProcess.sendData(StringTransformer.getString(new NetworkMessage(_adminInputEducationalInstitution, Configuration.OVERDUE_OPERATION, payload)));
					log.info(_response);
					Configuration.showWelcomeMenu();
					break;

				// Logic for book reservation
				case 3:
					System.out.println("Please enter the details below to reserve a book >>>>>>>");

					Scanner _bookReservationInput = new Scanner(System.in);
					System.out.println(_requestEducationalInstitution);
					_adminInputEducationalInstitution = _bookReservationInput.next().trim();
					while (!(_adminInputEducationalInstitution.toLowerCase().equals("concordia")
							|| _adminInputEducationalInstitution.toLowerCase().equals("mcgill") || _adminInputEducationalInstitution
							.toLowerCase().equals("dawson"))) {

						log.info("Please choose from the following instiutions : concordia/mcgill/dawson");
						_adminInputEducationalInstitution = _keyboardInputGeneralMenu.next().trim();
						if (!"concordia".equals(_adminInputEducationalInstitution.toLowerCase())
								|| !"mcgill".equals(_adminInputEducationalInstitution.toLowerCase())
								|| !"dawson".equals(_adminInputEducationalInstitution.toLowerCase())) {
							continue;
						} else
							break;
					}
					System.out.println("Enter Username");
					_userInputUserName = _bookReservationInput.next().trim();
					System.out.println("Enter Password");
					_userInputPassword = _bookReservationInput.next().trim();
					System.out.println(_requestBookName);
					_userInputbookName = _bookReservationInput.next();
					System.out.println("Enter author name");
					_authorName = _bookReservationInput.next();
					System.out.println("\n");
					System.out.println("Waiting................");
					destination = _adminInputEducationalInstitution;
					payload = StringTransformer.getString(new Reservation(_userInputUserName, _userInputPassword, _userInputbookName, _authorName, _adminInputEducationalInstitution));
					_response = clientProcess.sendData( StringTransformer.getString( new NetworkMessage(destination, Configuration.RESERVATION_OPERATION, payload)));
					log.info(_response);
					Configuration.showWelcomeMenu();
					break;
				case 4:
					log.info("You chose to exit the application. Have a nice day !");
					_keyboardInputGeneralMenu.close();
					System.exit(0);

				default:
					log.info("Invalid Input, please try again.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

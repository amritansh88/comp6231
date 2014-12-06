package drms.org.model;



/**
 * Template of an account.  
 */
public class Account {
	
	
	
	private String first;
	private String last;
	private String email;
	private String telephone;
	private String username;
	private String password;
	private String institution;
	private boolean admin;

	
	
	
	
	public Account() {
		this("", "", "", "", "", "", "");
	}

	public Account(String first, String last, String email, String telephone, String username, String password,
			String institution) {
		this.first = first;
		this.last = last;
		this.email = email;
		this.telephone = telephone;
		this.username = username;
		this.password = password;
		this.institution = institution;
		this.admin = false;
	}



	public String getFirst() {
		return first;
	}


	public void setFirst(String first) {
		this.first = first;
	}








	public String getLast() {
		return last;
	}








	public void setLast(String last) {
		this.last = last;
	}








	public String getEmail() {
		return email;
	}








	public void setEmail(String email) {
		this.email = email;
	}








	public String getTelephone() {
		return telephone;
	}








	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}








	public String getUsername() {
		return username;
	}








	public void setUsername(String username) {
		this.username = username;
	}








	public String getPassword() {
		return password;
	}








	public void setPassword(String password) {
		this.password = password;
	}








	public boolean isAdmin() {
		return admin;
	}








	public void setAdmin(boolean admin) {
		this.admin = admin;
	}








	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}





}

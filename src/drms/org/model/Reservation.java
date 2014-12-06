package drms.org.model;

import java.util.Date;


public class Reservation{
	

	private Book book;
	private Account account;
	private Date starting;
	private Date dueDate;
	private Date returnDate;
	private Long fees;
	private int days;
	
	
	public Reservation( String username, String password, String title, String author, String library){
		book = new Book( title, author ); 
		account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		account.setInstitution(library);
		book.setLibrary(library);
	}
	
	
	public Reservation(){
		this( new Book(), new Account(), null, null, null );
	}
	
	
	
	
	
	public Reservation(Book book, Account account, Date starting, Date dueDate, Date returnDate) {
		this.book = book;
		this.account = account;
		this.starting = starting;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getStarting() {
		return starting;
	}

	public void setStarting(Date starting) {
		this.starting = starting;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Long getFees() {
		return fees;
	}

	public void setFees(Long fees) {
		this.fees = fees;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
	
	
	
}
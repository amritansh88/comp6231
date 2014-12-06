package drms.org.model;


public class Book{
	
	
	
	private String title;
	private String author;
	private String library; 
	private boolean reserved;
	
	public Book( String title, String author, String code , String library){
		this( title, author, code );
		this.setLibrary(library);
	}
	public Book( String title, String author, String code ){
		this.title  = title;
		this.author = author;
		this.reserved = false;
	}
	public Book( String title, String author){
		this(title, author, "");
	}
	
	
	/**
	 * This constructor is used for initialization purposes only 
	 */
	public Book() {
		this( null, null, null );
	}
	public  String getTitle() {
		return title;
	}
	public  void setTitle(String title) {
		this.title = title;
	}
	public  String getAuthor() {
		return author;
	}
	public  void setAuthor(String author) {
		this.author = author;
	}
	
	public boolean isReserved() {
		return reserved;
	}
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	

	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}
	

}
package in.hemangrami.model;

public class Message {

	private int id;
	private String subject;
	private String message;
	private String usertoken;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsertoken() {
		return usertoken;
	}
	public void setUsertoken(String userToken) {
		this.usertoken = userToken;
	}
}

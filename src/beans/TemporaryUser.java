package beans;

public class TemporaryUser {
	private String email;
	private String password;
	
	public TemporaryUser(String email, String password){
		this.setEmail(email);
		this.setPassword(password);
	}
	
	public TemporaryUser(){
		this.email = null;
		this.password = null;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

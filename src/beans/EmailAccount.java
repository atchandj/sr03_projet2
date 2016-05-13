package beans;

public class EmailAccount {
	private String login;
	private String password;
	
	public EmailAccount(String login, String password){
		this.setLogin(login);
		this.setPassword(password);
	}
	
	public EmailAccount(){
		this.login = null;
		this.password = null;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}
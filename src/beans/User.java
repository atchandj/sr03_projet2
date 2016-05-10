package beans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class User {
	private String email;
	private String password;
	private String surname;
	private String name;
	private String phone;
	private String company;
	private Timestamp accountCreation;
	private boolean accountStatus;
	
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAccountCreation() {
		String formattedAccountCreation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.accountCreation);
		return formattedAccountCreation;
	}
	public void setAccountCreation(Timestamp accountCreation) {
		this.accountCreation = accountCreation;
	}
	public String getAccountStatus() {
		if(accountStatus){
			return "Le compte est activé";
		}else{
			return "Le compte n'est pas activé";
		}
	}
	public void setAccountStatus(boolean accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public boolean isActive(){
		return this.accountStatus;
	}
}
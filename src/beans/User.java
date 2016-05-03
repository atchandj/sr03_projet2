package beans;

import java.sql.Date;

public class User {
	private String email;
	private String surname;
	private String name;
	private String phone;
	private String company;
	private Date accountCreation;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Date getAccountCreation() {
		return accountCreation;
	}
	public void setAccountCreation(Date accountCreation) {
		this.accountCreation = accountCreation;
	}
	public boolean isAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(boolean accountStatus) {
		this.accountStatus = accountStatus;
	}
	private boolean accountStatus;
}
 
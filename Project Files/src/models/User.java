package models;

import connection.UserDAO;

public class User {
	
	private String ID;
	private String username;
	private String password;
	private String phoneNumber;
	private String address;
	private String role;
	
	public User(String ID, String username, String password, String phoneNumber, String address, String role) {
		super();
		this.ID = ID;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
	}
	
//	public void Login(String username, String password) {
//		
//	}
	
	public boolean login() {
        UserDAO userDAO = new UserDAO();
        return userDAO.validateCredentials(this.username, this.password);
    }
	
	public void Register(String username, String password, String phoneNumber, String address) {
		
	}
	
	public void CheckAccountValidation(String username, String password, String phoneNumber, String address) {
		
	}
	
	

	public String getID() {
		return ID;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public String getRole() {
		return role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	

}

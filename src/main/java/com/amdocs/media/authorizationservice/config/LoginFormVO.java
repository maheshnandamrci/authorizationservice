package com.amdocs.media.authorizationservice.config;

public class LoginFormVO {

	private String username;
	private String password;
	private String operation;
	private String address;
	private String phoneNumber;

	public LoginFormVO() {

	}

	public LoginFormVO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public LoginFormVO(String username, String password, String operation) {
		this.username = username;
		this.password = password;
		this.operation = operation;
	}

	public LoginFormVO(String username, String password, String operation,
			String address, String phoneNumber) {
		this.username = username;
		this.password = password;
		this.operation = operation;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}

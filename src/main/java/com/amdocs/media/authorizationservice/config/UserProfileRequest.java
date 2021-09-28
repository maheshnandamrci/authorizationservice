package com.amdocs.media.authorizationservice.config;

public class UserProfileRequest {

	private String address;
	private String phoneNumber;

	public UserProfileRequest() {
	}

	public UserProfileRequest(String address, String phoneNumber) {
		this.address = address;
		this.phoneNumber = phoneNumber;
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

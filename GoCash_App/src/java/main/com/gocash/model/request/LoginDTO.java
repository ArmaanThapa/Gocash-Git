package com.gocash.model.request;

import com.gocash.model.Utility;

public class LoginDTO  extends Utility{
	
	
	
	
	private String username;
	private String password;
	private boolean validate;
	private String mobileToken;
	
	
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
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public String getMobileToken() {
		return mobileToken;
	}
	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}
	
	
	

}

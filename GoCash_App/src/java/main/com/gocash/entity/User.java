package com.gocash.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


import org.hibernate.annotations.GenericGenerator;

import com.gocash.model.Status;
import com.gocash.model.UserType;



@Entity
public class User extends AbstractEntity<Long> {
	
	
	private static final long serialVersionUID = 1L;
	
	
	/*@Id
	@GenericGenerator(name="ref", strategy="increment")
	@GeneratedValue(generator="ref")
	@Column
	private int id;*/
	
	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String email;
	
	
	@Column(nullable = false)
	private String firstname;


	@Column(nullable = false)
	private UserType userType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status emailStatus;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status mobileStatus;
	
	

	@OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private UserDetail userDetail;
	
	
	@Column
	private String mobileToken;
	
	@Column(nullable = false)
	private String authority;
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getMobileToken() {
		return mobileToken;
	}

	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Status getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(Status emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Status getMobileStatus() {
		return mobileStatus;
	}

	public void setMobileStatus(Status mobileStatus) {
		this.mobileStatus = mobileStatus;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	
	
	
	
	
	

}

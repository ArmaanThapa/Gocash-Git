package com.gocash.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
public class UserSession  extends AbstractPersistable<Long>{

	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false, unique = true)
	private String sessionId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private User user;

	@Column(nullable = false)
	private boolean expired;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastRequest;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public Date getLastRequest() {
		return lastRequest;
	}

	public void setLastRequest(Date lastRequest) {
		this.lastRequest = lastRequest;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}



}

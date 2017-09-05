package com.gocash.api.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gocash.api.ISessionApi;
import com.gocash.api.IUserApi;
import com.gocash.entity.User;
import com.gocash.entity.UserSession;
import com.gocash.model.request.RegisterDTO;
import com.gocash.repositories.UserRepository;
import com.gocash.repositories.UserSessionRepository;
import com.gocash.session.UserDetailsWrapper;
import com.gocash.util.ClientException;

public class SessionApi implements ISessionApi {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	

	private final UserSessionRepository userSessionRepository;
	private final UserRepository userRepository;
	
	public SessionApi(UserSessionRepository userSessionRepository,UserRepository userRepository ) {
		this.userSessionRepository=userSessionRepository;
		this.userRepository=userRepository;
		
	}

	@Override
	public boolean checkActiveSession(User user) {
		
		boolean valid = true;
		if(user!=null)
		{
			UserSession session = userSessionRepository.findByActiveUserSession(user);
			logger.info("session :: " + session);
			
			if (session != null) {
				System.err.println("nottttttttttttttttttttttttttttttttttttttttt");
				logger.info("Expired :: " + session.isExpired());
				if (!session.isExpired()) {
					session.setExpired(true);
					userSessionRepository.save(session);
					logger.info("Session id :: " + session.getId());
				} else {
					logger.info("Session Expired..222!!");
				}
			}
			
			 else {
					System.err.println("eeeeeeeeeeeeeeeeeeeeeeee");
					logger.info("Session Expired..!!");
				}
			
		}
		
		logger.info("Valid :: " + valid);
		return valid;
	}

	@Override
	public void expireSession(String sessionId) {

		UserSession session = getUserSession(sessionId);
		session.setExpired(true);
		userSessionRepository.save(session);
	
		
	}

	@Override
	public List<UserSession> getAllUserSession(Long id, boolean includeExpiredSessions) {
		

		if (includeExpiredSessions) {
			return userSessionRepository
					.getUserSessionsIncludingExpired(id);
		}
		return userSessionRepository.getUserSessions(id);
	
	}

	@Override
	public UserSession getUserSession(String sessionId) {
		return userSessionRepository.findBySessionId(sessionId);
	}

	@Override
	public void refreshSession(String sessionId) {

		userSessionRepository.refreshSession(sessionId);
	
		
		
	}

	@Override
	public void registerNewSession(String sessionId, UserDetailsWrapper principal) {
		System.err.println(sessionId+"   sessionId");
		UserSession session=userSessionRepository.findBySessionId(sessionId);
		if(session==null)
		{
			session = new UserSession();
			session.setSessionId(sessionId);
		}
		session.setUser(userRepository.findByUsername(principal.getUsername()));
		session.setLastRequest(new Date());
		System.err.println("before saving into the database nanu");
		userSessionRepository.save(session);
		
		
		
	}

	@Override
	public void removeSession(String tokenKey) {
		

		UserSession userSession = userSessionRepository
				.findBySessionId(tokenKey);
		if (userSession != null) {
			userSessionRepository.delete(userSession);
		}
	
		
		
	}

}

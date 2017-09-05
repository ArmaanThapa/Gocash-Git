package com.gocash.api.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gocash.api.ISessionLogApi;
import com.gocash.entity.SessionLog;
import com.gocash.entity.UserSession;
import com.gocash.repositories.SessionLogRepository;



public class SessionLogApi implements ISessionLogApi {
	
	private final SessionLogRepository sessionLogRepository;
	
	public SessionLogApi(SessionLogRepository sessionLogRepository) {
		this.sessionLogRepository=sessionLogRepository;
	}
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Override
	public void logUserLoggedIn(Long userId, String sessionId, String remoteAddr, 
			String machineId, String simId) {
		logger.debug("Logging user Logged in:" + userId);
		
		
		
		
		SessionLog session =new SessionLog();
		session.setUserId(userId);
		session.setLoggedIn(new Date());
		session.setSessionId(sessionId);
		session.setRemoteAddress(remoteAddr);
		session.setMachineId(machineId);
		session.setSimId(simId);
		
		System.err.println("userId  "+userId);
		System.err.println(sessionId+"  sessionId");
		System.err.println(remoteAddr+ "  remoteAddress");
		System.err.println(machineId +"machineId");
		System.err.println(simId +"simId");
		System.err.println(session.getMachineId());
		System.err.println(session.getSessionId());
		System.err.println("lllllllllllllllllllllllllllllllll");
		sessionLogRepository.save(session);
		
		
	}

	

}

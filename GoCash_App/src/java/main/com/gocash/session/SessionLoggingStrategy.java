package com.gocash.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

import com.gocash.api.ISessionLogApi;


public class SessionLoggingStrategy implements SessionAuthenticationStrategy,
ApplicationListener<HttpSessionDestroyedEvent>{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ConcurrentSessionControlStrategy sessionControlStrategy;
	private final ISessionLogApi sessionLogApi;
	
	
	public SessionLoggingStrategy(ConcurrentSessionControlStrategy sessionControlStrategy,ISessionLogApi sessionLogApi) {
		this.sessionControlStrategy=sessionControlStrategy;
		this.sessionLogApi=sessionLogApi;
	}
	
	
	

	@Override
	public void onAuthentication(Authentication auth, HttpServletRequest request, HttpServletResponse response)
			throws SessionAuthenticationException {
		System.err.println(auth+" auth");
		System.err.println(request);
		System.err.println(response);
		sessionControlStrategy.onAuthentication(auth, request, response);
		
		
		System.err.println("hello armaan thapa");
		String machineId = request.getHeader("machineId");
		String simId = request.getHeader("simId");
		String ipAddress = request.getHeader("ipAddress");
		logger.info("authentication==>" + auth);
		logger.info("user logged in from: " + ipAddress);
		logger.info("user logged in machine: " + machineId);
		logger.info("user logged in device: " + simId);
		Object principal = auth.getPrincipal();
		
		if (principal instanceof UserDetailsWrapper) {
			sessionLogApi.logUserLoggedIn(((UserDetailsWrapper) principal)
					.getUser().getId(), request.getSession().getId(),
					request.getRemoteAddr(), machineId, simId);
		}
		
		
		
		
	}

	@Override
	public void onApplicationEvent(HttpSessionDestroyedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

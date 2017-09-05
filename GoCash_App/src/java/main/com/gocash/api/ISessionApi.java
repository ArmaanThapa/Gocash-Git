package com.gocash.api;

import java.util.List;

import com.gocash.entity.User;
import com.gocash.entity.UserSession;
import com.gocash.session.UserDetailsWrapper;

public interface ISessionApi {

	boolean checkActiveSession(User user);

	void expireSession(String sessionId);

	List<UserSession> getAllUserSession(Long id, boolean includeExpiredSessions);

	UserSession getUserSession(String sessionId);

	void refreshSession(String sessionId);

	void registerNewSession(String sessionId, UserDetailsWrapper principal);

	void removeSession(String sessionId);

}

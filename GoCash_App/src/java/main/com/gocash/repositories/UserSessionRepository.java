package com.gocash.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gocash.entity.User;
import com.gocash.entity.UserSession;


public interface UserSessionRepository extends CrudRepository<UserSession, String>{
	
	@Query("select s from UserSession s WHERE s.user = ?1 and s.expired = false")
	UserSession findByActiveUserSession(User user);

	@Query("select s from UserSession s WHERE s.user.id = ?1")
	List<UserSession> getUserSessionsIncludingExpired(Long userId);
	
	
	@Query("select s from UserSession s WHERE s.user.id = ?1 and s.expired = false")
	List<UserSession> getUserSessions(Long userId);

	@Query("select s from UserSession s WHERE s.sessionId = ?1 and s.expired = false")
	UserSession findByActiveSessionId(String sessionId);

	@Query("select s from UserSession s WHERE s.sessionId = ?1")
	UserSession findBySessionId(String sessionId);

	@Transactional
	@Modifying
	@Query("update UserSession s set s.lastRequest = now() where s.sessionId=?1")
	void refreshSession(String sessionId);


}

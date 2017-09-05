package com.gocash.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gocash.entity.SessionLog;

public interface SessionLogRepository  extends CrudRepository<SessionLog,Long>,JpaSpecificationExecutor<SessionLog>{
	


	@Query("select s from SessionLog s where s.sessionId=?1")
	SessionLog findBySessionId(String sessionId);

	

}

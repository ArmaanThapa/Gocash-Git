package com.gocash.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gocash.entity.LoginLog;
import com.gocash.entity.User;
import com.gocash.model.Status;

public interface LoginLogRepository extends CrudRepository<LoginLog, Long>, JpaSpecificationExecutor<LoginLog> {

	
	
	
	@Query("select c from LoginLog c where c.created > CURRENT_DATE and c.user=?1 and c.status=?2")
	List<LoginLog> findTodayEntryForUserWithStatus(User user, Status status);
	
	@Modifying
	@Transactional
    @Query("update LoginLog c set c.status=?1 where c.id =?2")
	int deleteLoginLogForId(Status status, long id);
	

}

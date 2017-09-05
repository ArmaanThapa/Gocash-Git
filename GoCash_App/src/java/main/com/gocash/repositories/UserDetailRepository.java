package com.gocash.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gocash.entity.UserDetail;

public interface UserDetailRepository extends CrudRepository<UserDetail, Long>,JpaSpecificationExecutor<UserDetail> {
	
	
	@Query("select u from UserDetail u where u.email=?1")
	List<UserDetail> checkMail(String mail);

}

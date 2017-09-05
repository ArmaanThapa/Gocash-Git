package com.gocash.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gocash.entity.User;
import com.gocash.model.Status;


public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> 
{
	@Query("select u from User u where u.username=?1 and u.mobileStatus=?2")
	User findByUsernameAndStatus(String username, Status status);
	
	@Query("select u from User u where u.username=?1 and u.mobileStatus=?2 and u.emailStatus=?3")
	User findByUsernameAndMobileStatusAndEmailStatus(String username, Status mobileStatus, Status emailStatus);
	
	@Query("select u from User u where u.username=?1")
	User findByUsername(String username);
	
	
	




	
}

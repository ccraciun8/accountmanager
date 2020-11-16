package com.assignment.accountmgr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.classes.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// Add method for retrieving a user based on the username. 
	@Query("SELECT user FROM User user WHERE user.username = ?1")
	User getUserForUsername(@Param("username") String username);
	
}
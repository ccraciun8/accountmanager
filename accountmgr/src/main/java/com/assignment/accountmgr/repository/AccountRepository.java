package com.assignment.accountmgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.accountmgr.classes.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	// Add method for finding all accounts for a specific customer id
	@Query("SELECT acc FROM Account acc WHERE acc.customerId = ?1")
	List<Account> findAccountsForCustomerId(@Param("customerId") Long customerId);
	
}
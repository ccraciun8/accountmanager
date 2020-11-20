package com.assignment.accountmgr.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.accountmgr.classes.Account;
import com.assignment.accountmgr.repository.AccountRepository;
import com.assignment.accountmgr.utils.AccountUtils;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public AccountService() {
		
	}
	
	public List<Account> getAllAccountsForUserId(Long userId){
		return accountRepository.findAccountsForCustomerId(userId);
	}
	
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	
	private Account addAccountWithDate(Account account, Date date) throws Exception {
		Long customerId = account.getCustomerId();
		List<Account> currentCustomerAccounts = accountRepository.findAccountsForCustomerId(customerId);
		
		// Check that the provided account and date are valid.
		AccountUtils.validateAccountOpenDate(date);
		AccountUtils.validateUserCanCreateAccount(account.getCustomerId(), account, currentCustomerAccounts);
		
		// Set the correct create date on the account and save it in the database
		// The saved account will be returned to the user.
		account.setCreatedOn(date);
		return accountRepository.save(account);
	}
	
	public Account createAccount(Account account) throws Exception{
		// Retrieve the current time, and add 2 Hours (convert to Bucharest timezone).
		// The current implementatin hardcodes the GMT+2 timezone.
		// TODO This could be refactored depending on the scale of the project, to address different timezones.
		Date date = new Date(System.currentTimeMillis());
		//Calendar cal = Calendar.getInstance(); 	// creates calendar
		//cal.setTime(date);               		// sets calendar time/date
		//cal.add(Calendar.HOUR_OF_DAY, 2);      	// adds one hour
		//date = cal.getTime();                   // returns new date object plus one hour
		return addAccountWithDate(account, date);
	}
	
	public Account getAccountById(Long id) throws Exception{
		Account account = accountRepository.findById(id).orElseThrow( () ->
			new Exception("Could not find account for id " + id.toString())
		);
		return account;
	}

	
}

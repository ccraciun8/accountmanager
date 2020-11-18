package com.assignment.accountmgr;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.classes.Account;
import com.assignment.utils.AccountUtils;

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
		
		// check that the provided account and date are valid.
		AccountUtils.validateAccountOpenDate(date);
		AccountUtils.validateUserCanCreateAccount(account.getCustomerId(), account, currentCustomerAccounts);
		
		// set the correct create date on the account and save it in the database
		// the saved account will be returned to the user.
		account.setCreatedOn(date);
		return accountRepository.save(account);
	}
	
	public Account createAccount(Account account) throws Exception{
		Date date = new Date(System.currentTimeMillis());
		return addAccountWithDate(account, date);
	}
	
	public Account getAccountById(Long id) throws Exception{
		Account account = accountRepository.findById(id).orElseThrow( () ->
			new Exception("Could not find account for id " + id.toString())
		);
		return account;
	}

	
}

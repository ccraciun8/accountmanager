package com.assignment.accountmgr;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.classes.Account;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public AccountService() {
		
	}
	
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	
	private void validateDate(Date date) {
		
	}
	private void validateNoAccounts(Account account) {
		
	}
	
	private Account addAccountWithDate(Account account, Date date) {
		this.validateDate(date);
		this.validateNoAccounts(account);
		
		account.setCreatedOn(date);
		return accountRepository.save(account);
	}
	
	public Account createAccount(Account account) {
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

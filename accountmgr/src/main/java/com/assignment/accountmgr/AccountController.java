package com.assignment.accountmgr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.classes.Account;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping()
	public List<Account> getAllAccounts(){
		return accountService.getAllAccounts();
	}
	
	@PostMapping()
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable(value="id") Long id) throws Exception{
		Account account = accountService.getAccountById(id);
		return ResponseEntity.ok().body(account);
	}
}

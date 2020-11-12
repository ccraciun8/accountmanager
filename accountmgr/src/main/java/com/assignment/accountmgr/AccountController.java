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

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/")
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	
	@PostMapping("/")
	public Account createAccount(@RequestBody Account account) {
		return accountRepository.save(account);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable(value="id") Long id) throws Exception{
		Account account = accountRepository.findById(id).orElseThrow( () ->
			new Exception("Could not find account for id " + id.toString())
		);
		return ResponseEntity.ok().body(account);
	}
}

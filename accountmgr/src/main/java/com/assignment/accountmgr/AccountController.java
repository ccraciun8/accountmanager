package com.assignment.accountmgr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.assignment.classes.Account;
import com.assignment.classes.User;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;
	
	@GetMapping()
	public List<Account> getAllAccounts(@RequestParam(required = false) String username) throws Exception{
		List<Account> accounts = null;
		if (username == null) {
			accounts = accountService.getAllAccounts();
		}
		else {
			User user = userService.getUserByUsername(username);
			if (user == null) {
				throw new Exception("The provided username does not exist: '" + username + "'.");
			}
			accounts = accountService.getAllAccountsForUserId(user.getId());
		}
		return accounts;
	}
	
	@PostMapping()
	public Account createAccount(@RequestBody Map<String, Object> params) {
		try {
			String customerName = (String)params.get("customerName");
			Long balance = ((Number)params.get("balance")).longValue();
			User user = userService.getUserByUsername(customerName);
			if (user == null) {
				throw new Exception("The provided username does not exist: '" + customerName + "'.");
			}
		
			Account account = new Account();
			account.setCustomerId(user.getId());
			account.setBalance(balance);
			return accountService.createAccount(account);	
		}
		catch(Exception ex) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "The provided account could not be created. Exception: " + ex.getMessage(), ex);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable(value="id") Long id) throws Exception{
		Account account = accountService.getAccountById(id);
		return ResponseEntity.ok().body(account);
	}
}

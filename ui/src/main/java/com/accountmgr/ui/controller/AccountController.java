package com.accountmgr.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.accountmgr.ui.model.Account;
import com.accountmgr.ui.service.RestService;

@Controller
public class AccountController {

	@Autowired
	private RestService restService;
	
    @RequestMapping(value="/account")
    public String account(){
        return "account";
    }
    
    @GetMapping(value="/account")
    public String account(Model model) {
      model.addAttribute("account", new Account());
      model.addAttribute("accounts", restService.getAccounts());
      model.addAttribute("error", "");
      return "account";
    }

    @PostMapping("/account")
    public String acocountSubmit(@ModelAttribute Account account, Model model) throws Exception {
      String error = "";
      try {
    	  restService.addAccount(account);  
      }
      catch(Exception ex) {
    	  error = ex.getMessage();
      }
      model.addAttribute("error", error);
      model.addAttribute("account", account);
      model.addAttribute("accounts", restService.getAccounts());
      return "account";
    }
    
    private List<Account> getAccounts() {
    	return this.restService.getAccounts();
    }

}
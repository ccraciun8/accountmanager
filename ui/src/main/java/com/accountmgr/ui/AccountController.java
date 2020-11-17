package com.accountmgr.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.accountmgr.ui.model.Account;

@Controller
public class AccountController {

    @RequestMapping(value="/account")
    public String account(){
        return "account";
    }
    
    @GetMapping(value="/account")
    public String account(Model model) {
      model.addAttribute("account", new Account());
      return "account";
    }

    @PostMapping("/account")
    public String acocountSubmit(@ModelAttribute Account account, Model model) {
      model.addAttribute("account", account);
      return "result";
    }

}
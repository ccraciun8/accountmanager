package com.assignment.accountmgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.assignment.classes.Account;

@SpringBootApplication
@EntityScan("com.assignment.classes")
public class AccountmgrApplication {

	@Autowired
    private AccountRepository accountRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(AccountmgrApplication.class, args);
	}
	
    @EventListener
    public void appReady(ApplicationReadyEvent event) {
    	ApplicationContext context = new ClassPathXmlApplicationContext("AccountBeans.xml");
		
		Account firstAccount = (Account)context.getBean("firstAccount");
		Account secondAccount = (Account)context.getBean("secondAccount");
		
		accountRepository.save(firstAccount);
		accountRepository.save(secondAccount);
    }
	
}

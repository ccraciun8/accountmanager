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
import com.assignment.classes.User;

@SpringBootApplication
@EntityScan("com.assignment.classes")
public class AccountmgrApplication {

	@Autowired
    private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
		
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
		
		ApplicationContext userContext = new ClassPathXmlApplicationContext("UserBeans.xml");
		
		User firstUser = (User)userContext.getBean("firstUser");
		User secondUser = (User)userContext.getBean("secondUser");
		
		userRepository.save(firstUser);
		userRepository.save(secondUser);
    }
	
}

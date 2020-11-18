package com.assignment.accountmgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.assignment.accountmgr.classes.Account;
import com.assignment.accountmgr.classes.User;
import com.assignment.accountmgr.classes.UserRole;
import com.assignment.accountmgr.repository.AccountRepository;
import com.assignment.accountmgr.repository.UserRepository;
import com.assignment.accountmgr.repository.UserRoleRepository;

@SpringBootApplication
@EntityScan("com.assignment.accountmgr.classes")
public class AccountmgrApplication {

	@Autowired
    private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
		
	public static void main(String[] args) {
		SpringApplication.run(AccountmgrApplication.class, args);
	}
	
    @EventListener
    public void appReady(ApplicationReadyEvent event) {
    	// add the users
    	ApplicationContext userContext = new ClassPathXmlApplicationContext("UserBeans.xml");
		
		User firstUser = (User)userContext.getBean("firstUser");
		User secondUser = (User)userContext.getBean("secondUser");
		User thirdUser = (User)userContext.getBean("thirdUser");
		
		userRepository.save(firstUser);
		userRepository.save(secondUser);
		userRepository.save(thirdUser);
		
		// add the user roles
		ApplicationContext userRoleContext = new ClassPathXmlApplicationContext("UserRoleBeans.xml");
		
		UserRole firstRole = (UserRole)userRoleContext.getBean("firstUserRole");
		UserRole secondRole = (UserRole)userRoleContext.getBean("secondUserRole");
		UserRole thirdRole = (UserRole)userRoleContext.getBean("thirdUserRole");
		
		userRoleRepository.save(firstRole);
		userRoleRepository.save(secondRole);
		userRoleRepository.save(thirdRole);
		
    	// add the accounts
    	ApplicationContext context = new ClassPathXmlApplicationContext("AccountBeans.xml");
		
		Account firstAccount = (Account)context.getBean("firstAccount");
		Account secondAccount = (Account)context.getBean("secondAccount");
		
		accountRepository.save(firstAccount);
		accountRepository.save(secondAccount);
		
    }
	
}

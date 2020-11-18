package com.assignment.accountmgr;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.accountmgr.classes.Account;
import com.assignment.accountmgr.repository.AccountRepository;
import com.assignment.accountmgr.utils.AccountUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test") // Set up a different profile for unit tests, to use in memory db
public class AccountRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AccountRepository accountRepository;

	@Before
	public void emptyDatabase() {
		// make sure each test will start on a clean database
		accountRepository.deleteAll();
	}
	
	@Test
	public void test_noAccounts() {
		List<Account> allFoundAccounts = accountRepository.findAll();
		assertTrue(allFoundAccounts.size() == 0);
	}

	@Test
	public void test_addSimpleAccount() throws Exception {
		// given
		String sDate1 = "12/11/2020 11:14:34";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Account account = new Account(1, 0, date1);
		entityManager.persist(account);
		entityManager.flush();

		// when
		List<Account> allFoundAccounts = accountRepository.findAll();

		// then
		assertTrue(allFoundAccounts.size() == 1);

		Account foundAccount = allFoundAccounts.get(0);
		assertTrue(AccountUtils.compareAccounts(account, foundAccount));
	}

	@Test
	public void test_addTwoAccounts() throws Exception {
		// given
		String sDate1 = "12/11/2020 11:14:34";
		String sDate2 = "09/11/2020 17:01:20";

		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate2);

		Account account1 = new Account(1, 0, date1);
		Account account2 = new Account(1, 0, date2);
		entityManager.persist(account1);
		entityManager.persist(account2);
		entityManager.flush();

		// when
		List<Account> allFoundAccounts = accountRepository.findAll();

		// then
		assertTrue(allFoundAccounts.size() == 2);

		assertTrue(AccountUtils.compareAccounts(account1, allFoundAccounts.get(0)));
		assertTrue(AccountUtils.compareAccounts(account2, allFoundAccounts.get(1)));
	}

	public void test_findAccountById() throws Exception{
		// given
		Long customerId = Long.parseLong("1");
		String sDate1 = "12/11/2020 11:14:34";

		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		
		Account account1 = new Account(customerId, 0, date1);
		entityManager.persist(account1);
		entityManager.flush();

		// when
		Account foundAccount = accountRepository.findById(account1.getId()).orElseThrow( () ->
			new Exception("Could not find account for id " + account1.getId()));
		
		// then
		assertTrue(AccountUtils.compareAccounts(account1, foundAccount));
	}
	
	@Test
	public void test_findAccountsForCustomerId() throws Exception {
		// given
		Long customerId = Long.parseLong("1");
		String sDate1 = "12/11/2020 11:14:34";

		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);

		Account account1 = new Account(customerId, 0, date1);
		entityManager.persist(account1);
		entityManager.flush();

		// when
		List<Account> allFoundAccounts = accountRepository.findAccountsForCustomerId(customerId);

		// then
		assertTrue(allFoundAccounts.size() == 1);
		assertTrue(AccountUtils.compareAccounts(account1, allFoundAccounts.get(0)));
	}

	@Test
	public void test_findAccountsForCustomerId2() throws Exception {
		// given
		Long customerId = Long.parseLong("1");
		String sDate1 = "12/11/2020 11:14:34";
		String sDate2 = "08/05/2020 15:01:51";

		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate2);

		Account account1 = new Account(customerId, 0, date1);
		Account account2 = new Account(customerId, 2, date2);
		entityManager.persist(account1);
		entityManager.persist(account2);
		entityManager.flush();

		// when
		List<Account> allFoundAccounts = accountRepository.findAccountsForCustomerId(customerId);

		// then
		assertTrue(allFoundAccounts.size() == 2);
		assertTrue(AccountUtils.compareAccounts(account1, allFoundAccounts.get(0)));
		assertTrue(AccountUtils.compareAccounts(account2, allFoundAccounts.get(1)));
	}

	@Test
	public void test_findAccountsForCustomerId3() throws Exception {
		// given
		Long customerId = Long.parseLong("1");
		Long otherCustomerId = Long.parseLong("2");
		String sDate1 = "12/11/2020 11:14:34";
		String sDate2 = "08/05/2020 15:01:51";

		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate2);

		Account account1 = new Account(customerId, 0, date1);
		Account account2 = new Account(otherCustomerId, 2, date2);
		entityManager.persist(account1);
		entityManager.persist(account2);
		entityManager.flush();

		// when
		List<Account> allFoundAccounts = accountRepository.findAccountsForCustomerId(customerId);
		
		// then
		assertTrue(allFoundAccounts.size() == 1);
		assertTrue(AccountUtils.compareAccounts(account1, allFoundAccounts.get(0)));
	}
	
}
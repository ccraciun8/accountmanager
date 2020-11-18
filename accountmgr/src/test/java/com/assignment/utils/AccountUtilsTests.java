package com.assignment.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.accountmgr.classes.Account;
import com.assignment.accountmgr.utils.AccountUtils;


@RunWith(SpringRunner.class)
public class AccountUtilsTests {
	
	@Test
	public void test_compareSameAccountRef() throws ParseException {
		String sDate1 = "12/11/2020 11:14:34";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Account account = new Account(1, 0, date1);
		
		assertTrue(AccountUtils.compareAccounts(account, account));
	}

	@Test
	public void test_compareDifferentAccounts() throws ParseException{
		String sDate1 = "12/11/2020 11:14:34";
		String sDate2 = "16/11/2020 12:20:34";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate2);
		
		Account account1 = new Account(1, 0, date1);
		Account account2 = new Account(2, 1999, date2);
		
		assertFalse(AccountUtils.compareAccounts(account1, account2));		
	}
	
	@Test
	public void test_compareDifferentAccountsSameUserId() throws ParseException{
		String sDate1 = "12/11/2020 11:14:34";
		String sDate2 = "13/11/2020 11:14:34";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate2);
		
		Account account1 = new Account(1, 2400, date1);
		Account account2 = new Account(1, 5000, date2);
		
		assertFalse(AccountUtils.compareAccounts(account1, account2));		
	}
	
	@Test
	public void test_compareDifferentAccountsSameDate() throws ParseException{
		String sDate1 = "12/11/2020 11:14:34";
		String sDate2 = "12/11/2020 11:14:34";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate2);
		
		Account account1 = new Account(1, 0, date1);
		Account account2 = new Account(2, 4000, date2);
		
		assertFalse(AccountUtils.compareAccounts(account1, account2));		
	}
	
	@Test
	public void test_compareDifferentAccountsSameBalance() throws ParseException{
		String sDate1 = "12/11/2020 11:14:34";
		String sDate2 = "14/11/2020 11:14:34";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate2);
		
		Account account1 = new Account(1, 2500, date1);
		Account account2 = new Account(2, 2500, date2);
		
		assertFalse(AccountUtils.compareAccounts(account1, account2));		
	}
	
	@Test
	public void test_validateAccountOpenDate_validDate() throws Exception{
		List<String> dates = Arrays.asList(
				"12/11/2020 11:14:34", //Thursday
				"10/11/2020 09:00:00", //Tuesday
				"14/09/2020 16:59:59", //Monday
				"30/10/2020 13:20:59", //Friday
				"04/11/2020 15:01:10"  //Wednesday
				);
		for (String sDate : dates) {
			Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
			AccountUtils.validateAccountOpenDate(date);
		} 
	}
	
	@Test(expected = Exception.class)
	public void test_validateAccountOpenDate_wrongHours1() throws Exception{
		String sDate = "12/11/2020 08:59:59";
		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
		AccountUtils.validateAccountOpenDate(date);
	}
	
	@Test(expected = Exception.class)
	public void test_validateAccountOpenDate_wrongHours2() throws Exception{
		String sDate = "12/11/2020 17:00:00";
		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
		AccountUtils.validateAccountOpenDate(date);
	}
	
	@Test(expected = Exception.class)
	public void test_validateAccountOpenDate_wrongHours3() throws Exception{
		String sDate = "12/11/2020 23:10:00";
		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
		AccountUtils.validateAccountOpenDate(date);
	}
	
	@Test(expected = Exception.class)
	public void test_validateAccountOpenDate_wrongHours4() throws Exception{
		String sDate = "12/11/2020 04:20:59";
		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
		AccountUtils.validateAccountOpenDate(date);
	}
	
	@Test(expected = Exception.class)
	public void test_validateAccountOpenDate_wrongDays1() throws Exception {
		String sDate = "08/11/2020 10:20:59"; // Sunday
		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
		AccountUtils.validateAccountOpenDate(date);
	}
	
	@Test(expected = Exception.class)
	public void test_validateAccountOpenDate_wrongDays2() throws Exception {
		String sDate = "17/10/2020 10:20:59"; // Saturday
		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
		AccountUtils.validateAccountOpenDate(date);
	}
	
	@Test(expected = Exception.class)
	public void test_validateAccountOpenDate_futureDate() throws Exception{	
		Date date = new Date(System.currentTimeMillis() + 1000);
		AccountUtils.validateAccountOpenDate(date);
	}
	
	@Test
	public void test_validateUserCanCreateAccount_valid() throws Exception {
		Long userId = Long.parseLong("1");
		String sDate = "09/11/2020 10:20:59"; // Monday
		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
		Account account = new Account(userId, 0, date);
		AccountUtils.validateUserCanCreateAccount(userId, account, new ArrayList<>());
	}
	
	@Test(expected = Exception.class)
	public void test_validateUserCanCreateAccount_wrongUserId() throws Exception {
		Long userId = Long.parseLong("1");
		Long otherUserId = Long.parseLong("2");
		String sDate = "09/11/2020 10:20:59"; // Monday
		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
		Account account = new Account(userId, 0, date);
		AccountUtils.validateUserCanCreateAccount(otherUserId, account, new ArrayList<>());
	}
	
	@Test(expected = Exception.class)
	public void test_validateUserCanCreateAccount_UserAlreadyHasConfiguredAccounts() throws Exception {
		Long userId = Long.parseLong("1");
		
		String sDate1 = "09/11/2020 10:20:59"; // Monday
		String sDate2 = "10/11/2020 17:05:10"; // Tuesday
		Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
		
		Account account = new Account(userId, 0, date1);
		
		List<Account> userAccounts = new ArrayList<Account>();
		for (int i = 0; i < AccountUtils.MAX_NO_ACCOUNTS_PER_USER; i++) {
			userAccounts.add(new Account(userId, 0, date2));
		}
		AccountUtils.validateUserCanCreateAccount(userId, account, userAccounts);
	}
	
	@Test(expected = Exception.class)
	public void test_validateUserCanCreateAccount_AccountAlreadyExists() throws Exception {
		Long userId = Long.parseLong("1");
		
		String sDate = "09/11/2020 10:20:59"; // Monday
		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate);
		
		Account account = new Account(userId, 0, date);
		List<Account> userAccounts = Arrays.asList(account);
		AccountUtils.validateUserCanCreateAccount(userId, account, userAccounts);
	}
}
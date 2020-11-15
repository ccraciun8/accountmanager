package com.assignment.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.assignment.classes.Account;

public class AccountUtils {
	
	// helper method to compare two accounts.
	public static boolean compareAccounts(Account acc1, Account acc2) {
		if (acc1.getId() != acc2.getId()) {
			return false;
		}
		if (acc1.getCustomerId() != acc2.getCustomerId()) {
			return false;
		}
		if (acc1.getBalance() != acc2.getBalance()) {
			return false;
		}
		if (acc1.getCreatedOn() != acc2.getCreatedOn()) {
			return false;
		}
		return true;
	}

	// validate that an account can be opened at the given date
	// enforced rules - the day must be between Monday-Friday, and the hour between 09-17.
	public static void validateAccountOpenDate(Date date) throws Exception {
		String workingHoursMessage = "Accounts can only be opened from Monday to Friday, during working hours: 09-17.";
	    Calendar cal = Calendar.getInstance();
	    // set the input date in a calendar instance, in order to retrieve current day and hour
	    cal.setTime(date);
	    
	    // retrieve day of week as an int
	    // starts from value 1 (Sunday) -> 2 (Monday) -> .. -> 7 (Saturday)
	    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	    int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
	    
	    if (dayOfWeek == 1 || dayOfWeek == 7) {
	    	throw new Exception("Accounts cannot be opened on weekends. " + workingHoursMessage);
	    }
	    if (hourOfDay < 9 || hourOfDay >= 17) {
	    	throw new Exception("Accounts cannot be opened outside of working hours. " + workingHoursMessage);
	    }
	    
	    Date currentDate = new Date();
	    if (currentDate.before(date)) {
	    	throw new Exception("Cannot create accounts with 'future' dates.");
	    }
	}
	
	// validate that the user can create this account
	public static void validateUserCanCreateAccount(Long userId, Account account, List<Account> currentUsersAccounts) throws Exception{
		if (userId != account.getCustomerId()) {
			throw new Exception("The current user id (" + userId + ") is different than the user of the new account (" + account.getCustomerId() + ").");
		}
		
		for (Account acc : currentUsersAccounts) {
			if (AccountUtils.compareAccounts(account,  acc)) {
				throw new Exception("The provided account already exists.");
			}
		}
		
		if (currentUsersAccounts.size() > 0) {
			throw new Exception("The current user (id " + userId + ") already has " + currentUsersAccounts.size() + " opened accounts. A user can currently only have one account.");
		}
	}
}
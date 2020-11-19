package com.assignment.accountmgr.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.assignment.accountmgr.classes.Account;

public class AccountUtils {
	
	public final static int WORKING_HOURS_START = 9; // Start of working hours
	public final static int WORKING_HOURS_STOP = 17; // End of working hours
	
	// The requirement was for a max of 1 account per user. 
	// Currently enabling 5 accounts for debugging the framework
	public final static int MAX_NO_ACCOUNTS_PER_USER = 5; 
			
	// Helper method to compare two accounts.
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

	// Validate that an account can be opened at the given date
	// Enforced rules - the day must be between Monday-Friday, and the hour between 09-17.
	public static void validateAccountOpenDate(Date date) throws Exception {
		String workingWours = WORKING_HOURS_START + "-" + WORKING_HOURS_STOP + ".";
		String workingHoursMessage = "Accounts can only be opened from Monday to Friday, during working hours: " + workingWours;
	    Calendar cal = Calendar.getInstance();
	    // Set the input date in a calendar instance, in order to retrieve current day and hour
	    cal.setTime(date);
	    
	    // Retrieve day of week as an int
	    // Starts from value 1 (Sunday) -> 2 (Monday) -> .. -> 7 (Saturday)
	    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	    int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
	    
	    if (dayOfWeek == 1 || dayOfWeek == 7) {
	    	throw new Exception("Accounts cannot be opened on weekends. " + workingHoursMessage);
	    }
	    if (hourOfDay < WORKING_HOURS_START || hourOfDay >= WORKING_HOURS_STOP) {
	    	throw new Exception("Accounts cannot be opened outside of working hours. " + workingHoursMessage);
	    }
	    
	    Date currentDate = new Date();
	    if (currentDate.before(date)) {
	    	throw new Exception("Cannot create accounts with 'future' dates.");
	    }
	}
	
	// Validate that the user can create this account
	public static void validateUserCanCreateAccount(Long userId, Account account, List<Account> currentUsersAccounts) throws Exception{
		if (userId != account.getCustomerId()) {
			throw new Exception("The current user id (" + userId + ") is different than the user of the new account (" + account.getCustomerId() + ").");
		}
		
		for (Account acc : currentUsersAccounts) {
			if (AccountUtils.compareAccounts(account,  acc)) {
				throw new Exception("The provided account already exists.");
			}
		}
		
		if (currentUsersAccounts.size() >= MAX_NO_ACCOUNTS_PER_USER) {
			throw new Exception("The current user (id " + userId + ") already has " + currentUsersAccounts.size() + " opened accounts. A user can currently only have " + MAX_NO_ACCOUNTS_PER_USER + " account(s).");
		}
	}
}
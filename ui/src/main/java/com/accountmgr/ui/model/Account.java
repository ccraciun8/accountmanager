package com.accountmgr.ui.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Account {

	public Account() {
		
	}
	
	public Account(long balance) {
		super();
		this.balance = balance;
		}

	private long balance;
	private long id;
	private long customerId;
	private String createdOn;
	
	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	// The date is retrieved from the DB in a format similar to: "2020-11-18T15:36:51.457+00:00"
	// We will parse it back to a Date format in order to print it in a more readable format.
	public String getFormattedDate() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		Date date = dateFormat.parse(getCreatedOn());
		
		DateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return newDateFormat.format(date);
	}
}
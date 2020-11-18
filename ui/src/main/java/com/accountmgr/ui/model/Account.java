package com.accountmgr.ui.model;

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

}
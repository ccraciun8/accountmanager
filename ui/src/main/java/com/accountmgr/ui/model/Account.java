package com.accountmgr.ui.model;

public class Account {

	public Account() {
		
	}
	
	public Account(long balance) {
		super();
		this.balance = balance;
		}

	private long balance;

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

}
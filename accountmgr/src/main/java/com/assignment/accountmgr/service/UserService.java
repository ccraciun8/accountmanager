package com.assignment.accountmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.accountmgr.classes.User;
import com.assignment.accountmgr.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserService() {	
	}

	public User getUserByUsername(String username) {
		return userRepository.getUserForUsername(username);
	}	
}
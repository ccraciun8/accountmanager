package com.assignment.accountmgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.classes.User;

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
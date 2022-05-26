package com.skilldistillery.daytrainer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.repository.UserRepository;

@Service
public class AuthSerivceImpl implements AuthService {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		Account newAccount = new Account();
		newAccount.setBalance(10000);
		user.setAccount(newAccount);
		//TODO: password strength validation, etc.
		//TODO: make sure username is unique
		user.setEnabled(true);
		user.setRole("standard");
		
		userRepo.saveAndFlush(user);
		
		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	

}

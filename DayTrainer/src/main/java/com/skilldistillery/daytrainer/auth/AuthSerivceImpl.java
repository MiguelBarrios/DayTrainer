package com.skilldistillery.daytrainer.auth;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.account.AccountRepository;
import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.user.UserRepository;

@Service
public class AuthSerivceImpl implements AuthService {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AccountRepository accountRepo;

	@Override
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));

		//TODO: password strength validation, etc.
		//TODO: make sure username is unique
		user.setEnabled(true);
		user.setRole("standard");
		user.setCreatedAt(LocalDateTime.now());
		userRepo.saveAndFlush(user);
		
		Account newAccount = new Account();
		newAccount.setBalance(10000);
		newAccount.setDeposit(10000);
		newAccount.setUser(user);
		accountRepo.saveAndFlush(newAccount);
		user.setAccount(newAccount);
		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	

}

package com.skilldistillery.daytrainer.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.user.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Double getAccountDeposits(String username) {
		User user = userRepo.findByUsername(username);
		Account account = user.getAccount();
		return account.getDeposit();
	}

}

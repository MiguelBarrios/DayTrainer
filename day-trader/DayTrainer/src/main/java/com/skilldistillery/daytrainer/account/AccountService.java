package com.skilldistillery.daytrainer.account;

import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.User;

public interface AccountService {


	Account getUserAccount(String username);
	Double getAccountDeposits(String username);

	Account createAccount(User user);

}

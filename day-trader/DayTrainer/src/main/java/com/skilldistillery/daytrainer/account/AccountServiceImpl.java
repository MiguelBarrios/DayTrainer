package com.skilldistillery.daytrainer.account;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	private final UserRepository userRepo;

	private final AccountRepository accountRepository;

	@Override
	public Account getUserAccount(String username) {
		return accountRepository.getAccountByUsername(username);
	}

	@Override
	public Double getAccountDeposits(String username) {
		User user = userRepo.findByUsername(username);
		Account account = user.getAccount();
		return account.getDeposit();
	}

	@Override
	public Account createAccount(User user) {
		Account account = new Account();
		account.setBalance(25_000);
		account.setDeposit(25_000);
		account.setUser(user);
		account = accountRepository.save(account);
		return account;
	}

}

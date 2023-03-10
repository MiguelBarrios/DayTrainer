package com.skilldistillery.daytrainer.account;

import java.security.Principal;

import com.skilldistillery.daytrainer.entities.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/account")
@RestController
@Configuration
@CrossOrigin({ "*", "http://localhost" })
@RequiredArgsConstructor
public class AccountController {
	
	private final AccountService accountService;

	@GetMapping
	public Account getUserAccount(Principal principal){
		return accountService.getUserAccount(principal.getName());
	}
	
	@GetMapping("/depositsum")
	public Double getTotalDeposited(Principal principal) {
		return accountService.getAccountDeposits(principal.getName());
	}


	@GetMapping("balance")
	public Double getAccountBalance(Principal principal){
		return accountService.getUserAccount(principal.getName()).getBalance();
	}
}

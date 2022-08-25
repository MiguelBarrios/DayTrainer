package com.skilldistillery.daytrainer.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.daytrainer.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	@Query("SELECT a FROM Account a WHERE a.user.username = :username")
	Account getAccountByUsername(@Param("username") String username);
}

package com.skilldistillery.daytrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.daytrainer.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}

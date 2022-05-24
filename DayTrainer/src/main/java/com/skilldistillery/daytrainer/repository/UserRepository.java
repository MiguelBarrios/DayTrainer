package com.skilldistillery.daytrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.daytrainer.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

}

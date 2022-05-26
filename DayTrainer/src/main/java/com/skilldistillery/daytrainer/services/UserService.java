package com.skilldistillery.daytrainer.services;

import com.skilldistillery.daytrainer.entities.User;

public interface UserService {
	User getUserById(int userId);

	User getUserByUsername(String username);

}

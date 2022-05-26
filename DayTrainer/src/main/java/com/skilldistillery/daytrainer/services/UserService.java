package com.skilldistillery.daytrainer.services;

import com.skilldistillery.daytrainer.entities.User;

public interface UserService {
	User getUserById(int userId, String string);

	User update(String name,  User user);

	void destroy(String name, int userId);


	void payDay();

	User getUserByUsername(String username);


}

package com.skilldistillery.daytrainer.auth;

import com.skilldistillery.daytrainer.entities.User;

public interface AuthService {
	
	public User register(User user);
	public User getUserByUsername(String username);
	

}

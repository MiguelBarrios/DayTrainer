package com.skilldistillery.daytrainer.services;

import java.util.List;

import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.User;

public interface UserService {
	User getUserById(int userId, String string);

	User update(String name,  User user);

	void destroy(String name, int userId);



	void payDay();

	User getUserByUsername(String username, String name);

	List<User> getAllUsers(String name);

	List<Comment> getAllCommentsByTradeId(String name, Integer tradeId);

	

	

}

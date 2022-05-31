package com.skilldistillery.daytrainer.services;

import java.util.List;
import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.User;

public interface UserService {
	public User getUserById(int userId, String string);

	public User update(String name, User user);

	public void destroy(String name, int userId);

	public void payDay();

	public User getUserByUsername(String username);

	public List<User> getAllUsers(String name);

	public List<Comment> getAllCommentsByTradeId(String name, Integer tradeId);
	
	public List<User> allUsers();

	public List<User> leadersList();
	
}

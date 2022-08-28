package com.skilldistillery.daytrainer.user;

import java.util.List;
import java.util.Map;

import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.User;

public interface UserService {
	public User getUserById(int userId, String string);

	public User update(String name, User user);

	public void destroy(String name, int userId);

	public void deposityFunds();

	public User getUserByUsername(String username);

	public List<User> getAllUsers(String name);

	public List<Comment> getAllCommentsByTradeId(String name, Integer tradeId);
	
	public List<User> allUsers();

	Map<String, Object> leaderBoard();
	
	public Double getBalance(String username);

	public List<User> getFollowingList(int userId);

	User update2(String name, User user);

}

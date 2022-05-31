package com.skilldistillery.daytrainer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.repository.AccountRepository;
import com.skilldistillery.daytrainer.repository.CommentRepository;
import com.skilldistillery.daytrainer.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private CommentRepository commRepo;

	@Override
	public User getUserById(int userId, String name) {
		User currentUser = userRepo.findByUsername(name);
		if (currentUser != null && currentUser.getRole().equals("admin")) {
			Optional<User> found = userRepo.findById(userId);
			User foundUser = found.get();
			if (foundUser != null) {
				return foundUser;
			}
		}
		return null;
	}

	@Override
	public User update(String name, User user) {
		User managed = userRepo.findByUsername(name);
		System.out.print("%%%%");
		System.out.print(user);
		if (managed != null) {	
			managed.setEnabled(user.isEnabled());
			managed.setPassword(user.getPassword());
			managed.setUsername(user.getUsername());
			managed.setEmail(user.getEmail());
			managed.setProfilePicture(user.getProfilePicture());
			managed.setBiography(user.getBiography());
			userRepo.saveAndFlush(managed);
			return managed;
		}
			return null;
	}

	@Override
	public User getUserByUsername(String username,String name) {
		//add authentication
		return userRepo.findByUsername(username);
	}

	@Override
	public void destroy(String name, int userId) {
		User currentUser = userRepo.findByUsername(name);
		Optional<User> op = userRepo.findById(userId);
		if (currentUser != null && currentUser.getRole().equals("admin") && op.get() != null) {
			User userToDelete= op.get();
			accRepo.deleteById(userToDelete.getAccount().getId());
			userRepo.delete(userToDelete);
		}
	}

	@Override
	public void payDay() {
		List<User> allUsers = userRepo.findAll();
		for (User user : allUsers) {
			double currentBalance = user.getAccount().getBalance();
			currentBalance += 200;
			System.out.println(currentBalance);
			user.getAccount().setBalance(currentBalance);
			accRepo.saveAndFlush(user.getAccount());
		}
	}

	@Override
	public List<User> getAllUsers(String name) {
		//add authentication
		return userRepo.findAll();
	}

	@Override
	public List<Comment> getAllCommentsByTradeId(String name, Integer tradeId) {
		//add authentication
		return null; //commRepo.getCommentsByTradeId(tradeId) ;
	}

}

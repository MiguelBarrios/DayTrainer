package com.skilldistillery.daytrainer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

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
		User currentUser = userRepo.findByUsername(name);
		if (currentUser != null && currentUser.getRole().equals("admin")) {
			return userRepo.save(user);
		}
		return null;
	}
	
	@Override
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void destroy(String name, int userId) {
		User currentUser = userRepo.findByUsername(name);
		if (currentUser != null && currentUser.getRole().equals("admin")) {
			userRepo.delete(userRepo.getById(userId));
		}
	}

	@Override
	public void payDay() {
		List<User> allUsers = userRepo.findAll();
		for (User user : allUsers) {
			double currentBalance = user.getAccount().getBalance();
			currentBalance += 200;
			user.getAccount().setBalance(currentBalance);
			System.out.print(user.getAccount().getBalance());
		}
		
	}

}

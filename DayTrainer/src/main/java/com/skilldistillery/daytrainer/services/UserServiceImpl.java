package com.skilldistillery.daytrainer.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.LeaderBoardRanking;
import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.repository.AccountRepository;
import com.skilldistillery.daytrainer.repository.CommentRepository;
import com.skilldistillery.daytrainer.repository.TradeRepository;
import com.skilldistillery.daytrainer.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AccountRepository accRepo;

	@Autowired
	private CommentRepository commRepo;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private TradeRepository tradeRepo;

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
		if (managed != null) {
			managed.setEnabled(user.isEnabled());
			//TODO: ENCRYPT PASSWORD
			//managed.setPassword(user.getPassword());
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
	public void destroy(String name, int userId) {
		User currentUser = userRepo.findByUsername(name);
		Optional<User> op = userRepo.findById(userId);
		if (currentUser != null && currentUser.getRole().equals("admin") && op.get() != null) {
			User userToDelete = op.get();
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
			double deposits = user.getAccount().getDeposit() + 200;
			user.getAccount().setDeposit(deposits);
			accRepo.saveAndFlush(user.getAccount());
		}
	}

	@Override
	public List<User> getAllUsers(String name) {
		// add authentication
		return userRepo.findAll();
	}

	@Override
	public List<Comment> getAllCommentsByTradeId(String name, Integer tradeId) {
		// add authentication
		return null;
	}// commRepo.getCommentsByTradeId(tradeId) ;

	public List<User> allUsers() {
		List<User> enableCheck = userRepo.findAll();
		for (User user : enableCheck) {
			int index = 0;
			if (!user.isEnabled()) {
				enableCheck.remove(index);
			}
			index++;
		}
		return enableCheck;
	}

	@Override
	public Map<String, Object> leaderBoard() {
		List<LeaderBoardRanking> rankings = new ArrayList<>();
		List<User> users = userRepo.findAll();
		List<String> stocks = tradeRepo.getCurrentlyHeldStocks();

		for (User user : users) {
			List<StockPosition> pos = tradeService.getUserPositions(user.getUsername());
			user.setPassword(null);
			double balance = user.getAccount().getBalance() - user.getAccount().getDeposit();
//			System.out.printf("%s balance: %f  deposits: %f leaderBalance: %f", user.getUsername(), user.getAccount().getBalance(), user.getAccount().getDeposit(), balance);
			LeaderBoardRanking cur = new LeaderBoardRanking(user, balance, pos);
			rankings.add(cur);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("data", rankings);
		map.put("stocks", stocks);

		return map;

	}

	@Override
	public List<User> leadersList() {
		List<User> temp = allUsers();
		User first = new User();
		first.setAccount(new Account());
		first.getAccount().setBalance(3);
		User second = new User();
		second.setAccount(new Account());
		second.getAccount().setBalance(2);
		User third = new User();
		third.setAccount(new Account());
		third.getAccount().setBalance(1);
		List<User> topThree = new ArrayList<>();
		for (int i = 0; i < temp.size(); i++) {
			User current = temp.get(i);
			if (first.getAccount().getBalance() < current.getAccount().getBalance()) {
				third = second;
				second = first;
				first = current;
			} else if (second.getAccount().getBalance() < current.getAccount().getBalance()) {
				third = second;
				second = current;
			} else if (third.getAccount().getBalance() < current.getAccount().getBalance()) {
				third = current;
			}

		}
		topThree.add(first);
		topThree.add(second);
		topThree.add(third);

		return topThree;

	}

	@Override
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override

	public Double getBalance(String username) {
		User newUser = userRepo.findByUsername(username);
		return newUser.getAccount().getBalance();
	}

	public List<User> getFollowingList(int userId) {
		Optional<User> u = userRepo.findById(userId);
		System.out.println(u.get());
		if (u.isPresent()) {
			List<User> following = u.get().getFollowing();
			return following;
		}
		return null;

	}

}

package com.skilldistillery.daytrainer.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.account.AccountRepository;
import com.skilldistillery.daytrainer.comment.CommentRepository;
import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.LeaderBoardRanking;
import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.trade.TradeRepository;
import com.skilldistillery.daytrainer.trade.TradeService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final PasswordEncoder encoder;
	private final UserRepository userRepo;
	private final AccountRepository accountRepo;
	private final TradeService tradeService;
	private final TradeRepository tradeRepo;

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
	public User update2(String name, User user) {
		User managed = userRepo.findByUsername(name);
		if(managed != null) {
			user.setUsername(user.getUsername());
			user.setPassword(encoder.encode(user.getPassword()));
			userRepo.saveAndFlush(user);
		}
		return user;
	}

	@Override
	public User update(String name, User user) {
		
		User managed = userRepo.findByUsername(name);
		if (managed != null) {
			managed.setEnabled(user.isEnabled());
			managed.setEmail(user.getEmail());
			managed.setFirstName(user.getFirstName());
			managed.setLastName(user.getLastName());
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
			accountRepo.deleteById(userToDelete.getAccount().getId());
			userRepo.delete(userToDelete);
		}
	}

	@Override
	public void deposityFunds() {
		List<Account> accounts = accountRepo.findAll();
		for(Account account : accounts) {
			double balance = account.getBalance() + 200;
			account.setBalance(balance);
			double deposits = account.getDeposit() + 200;
			account.setDeposit(deposits);
		}
		
		accountRepo.saveAllAndFlush(accounts);
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
			LeaderBoardRanking cur = new LeaderBoardRanking(user, balance, pos);
			rankings.add(cur);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("data", rankings);
		map.put("stocks", stocks);
		return map;

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
		if (u.isPresent()) {
			List<User> following = u.get().getFollowing();
			return following;
		}
		return null;
	}

	@Override
	public boolean isAvailable(String username, String newUsername) {
		User user = userRepo.findByUsername(newUsername);
		return user == null;
	}
	
}

package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.services.UserService;

@RequestMapping("api")
@RestController
@Configuration
@EnableScheduling
@CrossOrigin({ "*", "http://localhost" })
public class UserController {

	@Autowired
	private UserService userSvc;

	@GetMapping("users")
	public List<User> getAllUser(Principal principal, HttpServletResponse res) {
		List<User> users = userSvc.getAllUsers(principal.getName());
		if (users == null) {
			res.setStatus(404);
		}
		return users;
	}

	@GetMapping("users/commments/{tradeId}")
	public List<Comment> getAllUserComments(Principal principal, @PathVariable Integer tradeId,
			HttpServletResponse res) {
		List<Comment> comments = userSvc.getAllCommentsByTradeId(principal.getName(), tradeId);
		if (comments == null) {
			res.setStatus(404);
		}
		return comments;
	}

	@GetMapping("users/leaders")
	public Map<String, Object> getLeaders() {
		// List<User> test = userSvc.leadersList();
		// System.out.println(test);
		return userSvc.leaderBoard();

	}

	@GetMapping("users/{userId}")
	public User getUser(Principal principal, @PathVariable Integer userId, HttpServletResponse res) {
		User user = userSvc.getUserById(userId, principal.getName());
		if (user == null) {
			res.setStatus(404);
		}
		return user;
	}

	@GetMapping("users/name/{userName}")
	public User getUserByUserName(Principal principal, @PathVariable String userName, HttpServletResponse res) {
		User user = userSvc.getUserByUsername(userName);
		if (user == null) {
			res.setStatus(404);
		}
		return user;
	}
//	@GetMapping("users/{userName}")
//	public User getUserByUserName(@PathVariable String userName, HttpServletResponse res) {
//		User user = userSvc.getUserByUsername(userName);
//		if (user == null) {
//			res.setStatus(404);
//		}
//		return user;
//	}

	@PutMapping("users")
	public User updateUser(Principal principal, HttpServletRequest req, HttpServletResponse res,
			@RequestBody User user) {
		System.out.print("*******************testing");
		try {
			user = userSvc.update(principal.getName(), user);
			if (user == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}

		return user;
	}

	@DeleteMapping("users/{userId}")
	public void destroy(Principal principal, HttpServletRequest req, HttpServletResponse res,
			@PathVariable int userId) {
		userSvc.destroy(principal.getName(), userId);

	}

	// cron = "* * 1 * * MON-FRI",zone = "GMT-5"
	@Scheduled(fixedDelay = 7, timeUnit = TimeUnit.DAYS)
	public void payDay() {
		userSvc.payDay();
		System.out.println("pay Day!!");
	}
	
	@GetMapping("users/{userId}/following")
	public List<User> getUserFollowing(Principal principal, @PathVariable Integer userId, HttpServletResponse res) {
		
		List<User> followedUsers = userSvc.getFollowingList(userId);
//		for (Integer followed : followingIds) {
//			User u = userSvc.getUserById(followed, principal.getName());
//			followedUsers.add(u);
//		}
		return followedUsers;
		
	}
	
	@GetMapping("users/accountbalance")
	public Double getBalance(Principal principal) {
		return userSvc.getBalance(principal.getName());
		 
	}
}

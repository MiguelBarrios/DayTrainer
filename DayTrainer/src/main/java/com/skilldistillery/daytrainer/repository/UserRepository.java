package com.skilldistillery.daytrainer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.daytrainer.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
//	@Query("SELECT fu.users FROM User fu")
//	List<User> getUserFollowList(@Param("userId") int userId);

}

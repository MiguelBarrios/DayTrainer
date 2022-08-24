package com.skilldistillery.daytrainer.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.daytrainer.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
//	@Query("SELECT fu.users FROM User fu")
//	List<User> getUserFollowList(@Param("userId") int userId);

}

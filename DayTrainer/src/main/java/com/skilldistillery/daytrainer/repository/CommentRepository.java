package com.skilldistillery.daytrainer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.daytrainer.entities.Comment;

public interface CommentRepository  extends JpaRepository<Comment, Integer> {
	
//	@Query("SELECT c FROM Comment c WHERE c.trade_id = :tradeid")
//	List<Comment> getCommentsByTradeId(@Param("tradeid")int tradeid);
	
//	Account getAccountByUsername(@Param("username") String username);

}

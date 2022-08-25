package com.skilldistillery.daytrainer.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.daytrainer.entities.Comment;

public interface CommentRepository  extends JpaRepository<Comment, Integer> {
	
	@Query("SELECT c FROM Comment c WHERE trade_id = :tradeid")
	public List<Comment> getCommentsByTradeId(@Param("tradeid")int tradeid);

}

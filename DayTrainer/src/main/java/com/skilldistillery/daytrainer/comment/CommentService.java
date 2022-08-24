package com.skilldistillery.daytrainer.services;

import java.util.List;
import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.Trade;

public interface CommentService {

	public List<Comment> getComment(List<Trade> trades);

}

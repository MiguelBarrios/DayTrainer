package com.skilldistillery.daytrainer.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.Trade;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commRepo;

	@Override
	public List<Comment> getComment(List<Trade> trades) {
		
		List<Comment> allComments = new ArrayList<Comment>();
		List<Comment> tradeComments = new ArrayList<Comment>();
		allComments = commRepo.findAll();
		
		for (Trade trade : trades) {
			tradeComments.addAll(commRepo.getCommentsByTradeId(trade.getId()));	
		}
		 return tradeComments;
	}
}

package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.entities.Comment;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.services.CommentService;
import com.skilldistillery.daytrainer.services.TradeService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost"})
public class CommentController {
	
	@Autowired
	private CommentService commServ; 
	
	@Autowired
	private TradeService tradeServ;
	
	@GetMapping("comments")
	public List<Comment> getAll(HttpServletResponse response, Principal principal){
		return commServ.getComment(tradeServ.getUserTrades(principal.getName()));
	}
	
	
}

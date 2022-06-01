package com.skilldistillery.daytrainer.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;

public class FollowedUserTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private FollowedUser followedUser;
	
	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		FollowedUserId fUId= new FollowedUserId();
		fUId.setUserId(1);;
		fUId.setFollowedUserId(2);;
			}
	
}

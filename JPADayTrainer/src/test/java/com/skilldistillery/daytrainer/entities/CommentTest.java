package com.skilldistillery.daytrainer.entities;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Comment com;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("JPADayTrainer");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		com = em.find(Comment.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		com = null;
	}

	@Test
	@DisplayName("Initial Testing")
	void test1() {
		assertNotNull(com);
		assertEquals("Nice trade!",com.getContent());
		
	}
	@Test
	@DisplayName("User to Comments One to Many Mapping")
	void test2() {
		
		User temp = em.find(User.class, 1);
		assertNotNull(com);
		assertEquals(com.getUser().getId(),temp.getId());
		
	}
	@Test
	@DisplayName("Trade to Comments One to Many Mapping")
	void test3() {
		
		
		assertNotNull(com);
		assertEquals(com.getUser().getId(),1);
		
	}
} 


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

class HoldingTest {
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Holding hold;
	

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
		hold = em.find(Holding.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		hold = null;
	}

	@Test
	@DisplayName("Initial Testing")
	void test1() {
		assertNotNull(hold);
		assertEquals(100,hold.getAmount());
		
	}
	@Test
	@DisplayName("Holding to Stock One to One Mapping")
	void test2() {
		Stock temp = em.find(Stock.class, 1);
		assertNotNull(hold);
		assertEquals(temp.getId(),hold.getStock().getId());
		
	}
	@Test
	@DisplayName("Holding to User Many to One Mapping")
	void test3() {
		User temp = em.find(User.class, 1);
		assertNotNull(hold);
		assertEquals(temp.getId(),hold.getUser().getId());
		
	}

} 


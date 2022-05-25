package com.skilldistillery.daytrainer.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	private User user;
	

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
		user = em.find(User.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		user = null;
	}

	@Test
	void test1() {
		assertNotNull(user);
		assertEquals("admin",user.getUsername());
	}
	@Test
	@DisplayName("User to Account mapping")
	void test2() {
		Account temp = em.find(Account.class, 1);
		assertNotNull(user);
		assertNotNull(temp);
		assertEquals(temp.getBalance(),user.getAccount().getBalance());
	}
	@Test
	@DisplayName("User to Trade mapping")
	void test3() {
		assertNotNull(user);
		assertTrue(user.getTrades().size()>0);
	}
	@Test
	@DisplayName("User to Comments mapping")
	void test4() {
		assertNotNull(user);
		assertTrue(user.getComments().size()>0);
	}
	@Test
	@DisplayName("User to Comments mapping")
	void test5() {
		assertNotNull(user);
		assertTrue(user.getSentMessages().size()>0);
	}
	@Test

	@DisplayName("User to Comments mapping")
	void test6() {
		assertNotNull(user);
		assertTrue(user.getRecMessages().size()>0);
	}




}

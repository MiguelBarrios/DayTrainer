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

class AccountTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Account acc;

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
		acc = em.find(Account.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		acc = null;
	}

	@Test
	@DisplayName("Initial Testing")
	void test1() {
		assertNotNull(acc);
		assertEquals(1000.00, acc.getBalance());
		assertEquals(500.00, acc.getMarginAmount());
		assertTrue(acc.isMarginEnable());

	}

	@Test
	@DisplayName("Account to User mapping")
	void test2() {
		User temp = em.find(User.class, 1);
		assertNotNull(acc);
		assertNotNull(temp);
		assertEquals(temp.getAccount().getBalance(), acc.getBalance());
	}
}

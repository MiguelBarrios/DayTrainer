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

class TransactionTest {
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Transaction trans;
	

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
		trans = em.find(Transaction.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		trans = null;
	}

	@Test
	@DisplayName("Initial Testing")
	void test1() {
		assertNotNull(trans);
		assertEquals(3000,trans.getTotalPrice());
	}
	@Test
	@DisplayName("Transaction to Trade One to One mapping")
	void test2() {
		Trade temp = em.find(Trade.class, 1);
		assertNotNull(trans);
		assertEquals(temp.getId(),trans.getTrade().getId());
		
	}

} 
//id | total_price | account_id |
//+----+-------------+------------+
//|  1 |     3000.00 |          1 |
//+----+-------------+------------+

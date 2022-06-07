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

class TradeTest {
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Trade trade;
	

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
		trade = em.find(Trade.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		trade = null;
	}

	@Test
	@DisplayName("Initial Testing")
	void test1() {
		assertNotNull(trade);
		assertEquals(100,trade.getQuantity());
		
	}
	@Test
	@DisplayName("Trade to User mapping")
	void test3() {
		assertNotNull(trade);
		assertEquals(1,trade.getUser().getId());
	}
	@Test
	@DisplayName("Stock to Trade One to One mapping")
	void test4() {
		assertNotNull(trade);
		assertNotNull(trade.getStock());
		assertEquals("AAPL", trade.getStock().getSymbol());
		assertNotNull(trade);

	
	}

	@Test
	@DisplayName("Trade to Comment One to Many mapping")
	void test6() {
		
		assertNotNull(trade);
		assertTrue(trade.getComments().size()> 0);
		
	}
	
	@Test
	@DisplayName("MTO relationship to Order type")
	void test7() {
		assertNotNull(trade);
		OrderType type = trade.getOrderType();
		assertNotNull(type);
		assertEquals("Market", type.getName());
	}

} 


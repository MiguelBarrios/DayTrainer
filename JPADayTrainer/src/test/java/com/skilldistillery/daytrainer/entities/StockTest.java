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

class StockTest {
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Stock stock;
	

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
		stock = em.find(Stock.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		stock = null;
	}

	@Test
	@DisplayName("Initial Testing")
	void test1() {
		assertNotNull(stock);
		assertEquals("Apple Inc.",stock.getName());
		assertEquals("AAPL",stock.getSymbol());
	}
	@Test
	@DisplayName("Stock to Trade One to One mapping")
	void test2() {
		Trade temp = em.find(Trade.class, 1);
		assertNotNull(stock);
		assertEquals(temp.getId(),stock.getTrade().getId());
	
	}
	@Test
	@DisplayName("Stock to Holding One to One mapping")
	void test3() {
		Holding temp = em.find(Holding.class, 1);
		assertNotNull(stock);
		assertEquals(temp.getId(),stock.getHolding().getId());
		
	}

} 


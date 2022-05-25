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
		stock = em.find(Stock.class, "ORCL");
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
		assertEquals("Oracle Corp.",stock.getName());
		assertEquals("ORCL",stock.getSymbol());
	}
	@Test
	@DisplayName("Stock to Trade One to many mapping")
	void test2() {
		
		assertNotNull(stock);
		assertEquals(1,stock.getTrade().getId());
	
	}


} 


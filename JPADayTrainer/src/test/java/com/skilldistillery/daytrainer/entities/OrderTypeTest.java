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

class OrderTypeTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private OrderType orderType;
	
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
		orderType = em.find(OrderType.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		orderType = null;
	}

	@Test
	@DisplayName("Basic mapping test")
	void test1() {
		assertNotNull(orderType);
		assertEquals("Market", orderType.getName());
	}
}

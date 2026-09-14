package com.pgolmeda.padelbooking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

// @ServiceConnection levanta un MySQL real en Docker y conecta el datasource solo,
// sin tocar application.yml. Así el contexto arranca igual que en producción.
@Testcontainers
@SpringBootTest
class PadelBookingApplicationTests {

	@Container
	@ServiceConnection
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4");

	@Test
	void contextLoads() {
	}

}

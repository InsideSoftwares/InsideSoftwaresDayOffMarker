package br.com.insidesoftwares.dayoffmarker.repository.city;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnabledIfSystemProperty(named = "run.integration.postgres", matches = "true")
@ActiveProfiles("postgres")
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class CityRepositoryIntegrationPostgresqlTest {

	@Autowired
	private CityRepository cityRepository;

	@Container
	static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest");

	@DynamicPropertySource
	private static void setupProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
	}

	@Test
	void testTest(){
		assertEquals(0, cityRepository.findAll().size());
	}

}

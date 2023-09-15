package br.com.insidesoftwares.dayoffmarker.repository.state;

import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledIfSystemProperty(named = "run.integration.mysql", matches = "true")
@Sql(scripts = "classpath:mysql/insert_country_state_city.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:mysql/delete_country_state_city.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@Testcontainers
@ActiveProfiles("mysql")
class StateRepositoryIntegrationMysqlTest {

	@Autowired
	private StateRepository stateRepository;

	@Container
	static MySQLContainer container = new MySQLContainer("mysql:latest");

	@DynamicPropertySource
	private static void setupProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
	}

	@Test
	void shouldReturnStateWhenStateIdEqual3(){

		Optional<State> state = stateRepository.findStateById(3L);

		assertTrue(state.isPresent());
		assertEquals(3L, state.get().getId());
		assertEquals("RJ01", state.get().getCode());
	}

	@Test
	void shouldNotReturnStateWhenStateIdEqual53(){

		Optional<State> state = stateRepository.findStateById(53L);

		assertFalse(state.isPresent());
	}

}

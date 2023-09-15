package br.com.insidesoftwares.dayoffmarker.repository.country;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
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
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
@ActiveProfiles("mysql")
class CountryRepositoryIntegrationMysqlTest {

	@Autowired
	private CountryRepository countryRepository;

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
	void shouldReturnExistCountryWhenCodeBR01() {
		boolean exist = countryRepository.existsByCode("BR01");

		assertTrue(exist);
	}

	@Test
	void shouldntReturnExistCountryWhenCodeBR50() {
		boolean exist = countryRepository.existsByCode("BR50");

		assertFalse(exist);
	}

	@Test
	void shouldReturnCountryWhenNameBrasil() {
		Optional<Country> country = countryRepository.findCountryByName("Brasil");

		assertTrue(country.isPresent());
		assertEquals("Brasil",country.get().getName());
	}

	@Test
	void shouldReturnCountryWhenNameUnidos() {
		Optional<Country> country = countryRepository.findCountryByName("Unidos");

		assertTrue(country.isPresent());
		assertEquals("Estados Unidos",country.get().getName());
	}

	@Test
	void shouldntReturnCountryWhenNameCanada() {
		Optional<Country> country = countryRepository.findCountryByName("Canada");

		assertFalse(country.isPresent());
	}

	@Test
	void shouldReturnExistCountryWhenNameBrasil() {
		boolean exist = countryRepository.existsByName("Brasil");

		assertTrue(exist);
	}

	@Test
	void shouldntReturnExistCountryWhenNameCanada() {
		boolean exist = countryRepository.existsByName("Canada");

		assertFalse(exist);
	}

	@Test
	void shouldReturnExistCountryWhenAcronymBRS() {
		boolean exist = countryRepository.existsByAcronym("BRS");

		assertTrue(exist);
	}

	@Test
	void shouldntReturnExistCountryWhenAcronymCND() {
		boolean exist = countryRepository.existsByAcronym("CND");

		assertFalse(exist);
	}

	@Test
	void shouldReturnExistCountryWhenNameBrasilAndNotID2() {
		boolean exist = countryRepository.existsByNameAndNotId("Brasil", 2L);

		assertTrue(exist);
	}

	@Test
	void shouldntReturnExistCountryWhenNameCanadaAndNotID2() {
		boolean exist = countryRepository.existsByNameAndNotId("Canada", 2L);

		assertFalse(exist);
	}

	@Test
	void shouldReturnExistCountryWhenAcronymBRSAndNotID2() {
		boolean exist = countryRepository.existsByAcronymAndNotId("BRS", 2L);

		assertTrue(exist);
	}

	@Test
	void shouldntReturnExistCountryWhenAcronymCNDAndNotID2() {
		boolean exist = countryRepository.existsByAcronymAndNotId("CND", 2L);

		assertFalse(exist);
	}

	@Test
	void shouldReturnExistCountryWhenCodeBR01AndNotID2() {
		boolean exist = countryRepository.existsByCodeAndNotId("BR01", 2L);

		assertTrue(exist);
	}

	@Test
	void shouldntReturnExistCountryWhenCodeBR50AndNotID2() {
		boolean exist = countryRepository.existsByCodeAndNotId("BR50", 2L);

		assertFalse(exist);
	}

}

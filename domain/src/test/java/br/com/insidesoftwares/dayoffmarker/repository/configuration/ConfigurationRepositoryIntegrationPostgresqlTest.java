package br.com.insidesoftwares.dayoffmarker.repository.configuration;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Configuration;
import br.com.insidesoftwares.dayoffmarker.domain.repository.ConfigurationRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledIfSystemProperty(named = "run.integration.postgres", matches = "true")
@ActiveProfiles("postgres")
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class ConfigurationRepositoryIntegrationPostgresqlTest {

	@Autowired
	private ConfigurationRepository configurationRepository;

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
	void shouldReturnConfigurationWhenKeyLIMIT_BACK_DAYS_YEARS() {
		Optional<Configuration> configuration = configurationRepository.findConfigurationByKey(Configurationkey.LIMIT_BACK_DAYS_YEARS);

		assertTrue(configuration.isPresent());
	}

	@Test
	void shouldReturnConfigurationWhenKeyCOUNTRY_DEFAULT() {
		Optional<Configuration> configuration = configurationRepository.findConfigurationByKey(Configurationkey.COUNTRY_DEFAULT);

		assertTrue(configuration.isPresent());
	}

	@Test
	void shouldReturnConfigurationWhenKeyLIMIT_FORWARD_DAYS_YEARS() {
		Optional<Configuration> configuration = configurationRepository.findConfigurationByKey(Configurationkey.LIMIT_FORWARD_DAYS_YEARS);

		assertTrue(configuration.isPresent());
	}

}

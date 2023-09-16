package br.com.insidesoftwares.dayoffmarker.repository.configuration;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Configuration;
import br.com.insidesoftwares.dayoffmarker.domain.repository.ConfigurationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class ConfigurationRepositoryIntegrationTest {

	@Autowired
	private ConfigurationRepository configurationRepository;

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

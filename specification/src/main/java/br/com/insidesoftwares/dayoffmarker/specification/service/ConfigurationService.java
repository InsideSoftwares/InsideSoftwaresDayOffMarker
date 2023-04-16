package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationCountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationLimitYearRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.entity.Configuration;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ConfigurationService {

	Configuration findConfigurationByKey(final Configurationkey configurationKey);
	String findValueConfigurationByKey(final Configurationkey configurationKey);
	void updateConfiguration(final Configurationkey configurationKey, final String value);

    void configurationLimitYear(final @Valid ConfigurationLimitYearRequestDTO configurationLimitYearRequestDTO);

	void configurationCountry(final @Valid ConfigurationCountryRequestDTO configurationCountryRequestDTO);

}

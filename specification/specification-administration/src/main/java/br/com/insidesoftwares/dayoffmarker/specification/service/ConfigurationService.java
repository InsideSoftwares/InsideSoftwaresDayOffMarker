package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationCountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationLimitYearRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ConfigurationService {

    void updateConfiguration(final Configurationkey configurationKey, final String value);

    void configurationLimitYear(final @Valid ConfigurationLimitYearRequestDTO configurationLimitYearRequestDTO);

    void configurationCountry(final @Valid ConfigurationCountryRequestDTO configurationCountryRequestDTO);
}

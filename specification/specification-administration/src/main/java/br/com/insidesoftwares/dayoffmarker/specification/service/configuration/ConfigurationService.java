package br.com.insidesoftwares.dayoffmarker.specification.service.configuration;

import br.com.insidesoftwares.dayoffmarker.commons.dto.configuration.ConfigurationCountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.configuration.ConfigurationLimitYearRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ConfigurationService {

    void updateConfiguration(final Configurationkey configurationKey, final String value);
    void configurationLimitYear(final @Valid ConfigurationLimitYearRequestDTO configurationLimitYearRequestDTO);
    void configurationCountry(final @Valid ConfigurationCountryRequestDTO configurationCountryRequestDTO);
}

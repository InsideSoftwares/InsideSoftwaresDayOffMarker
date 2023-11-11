package br.com.insidesoftwares.dayoffmarker.specification.search.configuration;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.domain.entity.configuration.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ConfigurationSearchService {
    Configuration findConfigurationByKey(final Configurationkey configurationKey);

    String findValueConfigurationByKey(final Configurationkey configurationKey);
}

package br.com.insidesoftwares.dayoffmarker.specification.search;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ConfigurationSearchService {
    Configuration findConfigurationByKey(final Configurationkey configurationKey);

    String findValueConfigurationByKey(final Configurationkey configurationKey);
}

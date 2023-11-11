package br.com.insidesoftwares.dayoffmarker.service.configuration;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.domain.entity.configuration.Configuration;
import br.com.insidesoftwares.dayoffmarker.domain.repository.configuration.ConfigurationRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigurationSearchServiceBeanTest {

    private static final String VALUE_LIMIT_BACK_DAYS_YEARS = "3";
    private static final String VALUE_DEFAULT_LIMIT_BACK_DAYS_YEARS = "6";
    private static final String VALUE_LIMIT_FORWARD_DAYS_YEARS = "20";
    private static final String VALUE_DEFAULT_LIMIT_FORWARD_DAYS_YEARS = "25";
    private static final String VALUE_COUNTRY_DEFAULT = "Brazil";
    private static final String VALUE_DEFAULT_COUNTRY_DEFAULT = "Brazil";
    @Mock
    private ConfigurationRepository configurationRepository;
    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private ConfigurationSearchServiceBean configurationSearchServiceBean;

    @BeforeEach
    void before() {
        when(configurationRepository.findConfigurationByKey(Configurationkey.LIMIT_BACK_DAYS_YEARS))
                .thenReturn(Optional.of(createConfiguration(Configurationkey.LIMIT_BACK_DAYS_YEARS, VALUE_LIMIT_BACK_DAYS_YEARS, VALUE_DEFAULT_LIMIT_BACK_DAYS_YEARS)));
        when(configurationRepository.findConfigurationByKey(Configurationkey.LIMIT_FORWARD_DAYS_YEARS))
                .thenReturn(Optional.of(createConfiguration(Configurationkey.LIMIT_FORWARD_DAYS_YEARS, VALUE_LIMIT_FORWARD_DAYS_YEARS, VALUE_DEFAULT_LIMIT_FORWARD_DAYS_YEARS)));
        when(configurationRepository.findConfigurationByKey(Configurationkey.COUNTRY_DEFAULT))
                .thenReturn(Optional.of(createConfiguration(Configurationkey.COUNTRY_DEFAULT, VALUE_COUNTRY_DEFAULT, VALUE_DEFAULT_COUNTRY_DEFAULT)));
    }

    @Test
    void deveRetornaComSucessoConfiguracaoQuandoChaveExiste() {

    }

    private Configuration createConfiguration(final Configurationkey key, final String value, final String defaultValue) {
        return Configuration.builder()
                .key(key)
                .value(value)
                .defaultValue(defaultValue)
                .build();
    }

}

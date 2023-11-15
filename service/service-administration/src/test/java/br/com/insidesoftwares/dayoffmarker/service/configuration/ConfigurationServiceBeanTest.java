package br.com.insidesoftwares.dayoffmarker.service.configuration;

import br.com.insidesoftwares.dayoffmarker.commons.dto.configuration.ConfigurationCountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.configuration.ConfigurationLimitYearRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.configuration.Configuration;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.repository.configuration.ConfigurationRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.configuration.ConfigurationSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigurationServiceBeanTest {

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
    @Mock
    private ConfigurationSearchService configurationSearchService;
    @InjectMocks
    private ConfigurationServiceBean configurationServiceBean;


    @Test
    void shouldSuccessfullyUpdateConfiguration() {
        Configuration configuration = createConfiguration(Configurationkey.LIMIT_BACK_DAYS_YEARS, "1", "2");

        when(configurationSearchService.findConfigurationByKey(Configurationkey.LIMIT_BACK_DAYS_YEARS)).thenReturn(configuration);
        configuration.setValue("3");
        when(configurationRepository.save(configuration)).thenReturn(configuration);

        configurationServiceBean.updateConfiguration(Configurationkey.LIMIT_BACK_DAYS_YEARS, "3");

        verify(configurationSearchService, times(1)).findConfigurationByKey(Configurationkey.LIMIT_BACK_DAYS_YEARS);
        verify(configurationRepository, times(1)).save(configuration);
    }

    @Test
    void shouldSuccessfullyUpdateDefaultCountry() {
        Configuration configuration = createConfiguration(Configurationkey.COUNTRY_DEFAULT, VALUE_COUNTRY_DEFAULT, VALUE_DEFAULT_COUNTRY_DEFAULT);

        when(countryRepository.findCountryByName("Brasil")).thenReturn(createCountry());
        when(configurationSearchService.findConfigurationByKey(Configurationkey.COUNTRY_DEFAULT)).thenReturn(configuration);
        configuration.setValue("Brasil");
        when(configurationRepository.save(configuration)).thenReturn(configuration);

        configurationServiceBean.configurationCountry(createConfigurationCountryRequestDTO());

        verify(countryRepository, times(1)).findCountryByName("Brasil");
        verify(configurationSearchService, times(1)).findConfigurationByKey(Configurationkey.COUNTRY_DEFAULT);
        verify(configurationRepository, times(1)).save(configuration);
    }

    @Test
    void shouldReturnErrorWhenProvidingInvalidCountryName() {
        Configuration configuration = createConfiguration(Configurationkey.COUNTRY_DEFAULT, VALUE_COUNTRY_DEFAULT, VALUE_DEFAULT_COUNTRY_DEFAULT);

        when(countryRepository.findCountryByName("Brasil")).thenReturn(Optional.empty());

        assertThrows(CountryNameInvalidException.class, () -> configurationServiceBean.configurationCountry(createConfigurationCountryRequestDTO()));

        verify(countryRepository, times(1)).findCountryByName("Brasil");
        verify(configurationSearchService, times(0)).findConfigurationByKey(Configurationkey.COUNTRY_DEFAULT);
        verify(configurationRepository, times(0)).save(configuration);
    }

    @Test
    void shouldSuccessfullyUpdateDayLimits() {
        Configuration configurationLimitBackDays = createConfiguration(Configurationkey.LIMIT_BACK_DAYS_YEARS, VALUE_LIMIT_BACK_DAYS_YEARS, VALUE_DEFAULT_LIMIT_BACK_DAYS_YEARS);
        Configuration configurationLimitForwardDays = createConfiguration(Configurationkey.LIMIT_FORWARD_DAYS_YEARS, VALUE_LIMIT_FORWARD_DAYS_YEARS, VALUE_DEFAULT_LIMIT_FORWARD_DAYS_YEARS);

        when(configurationSearchService.findConfigurationByKey(Configurationkey.LIMIT_BACK_DAYS_YEARS)).thenReturn(configurationLimitBackDays);
        when(configurationSearchService.findConfigurationByKey(Configurationkey.LIMIT_FORWARD_DAYS_YEARS)).thenReturn(configurationLimitForwardDays);
        when(configurationRepository.save(configurationLimitBackDays)).thenReturn(configurationLimitBackDays);
        when(configurationRepository.save(configurationLimitForwardDays)).thenReturn(configurationLimitForwardDays);

        configurationServiceBean.configurationLimitYear(createConfigurationLimitYearRequestDTO());

        verify(configurationSearchService, times(1)).findConfigurationByKey(Configurationkey.LIMIT_BACK_DAYS_YEARS);
        verify(configurationSearchService, times(1)).findConfigurationByKey(Configurationkey.LIMIT_FORWARD_DAYS_YEARS);
        verify(configurationRepository, times(1)).save(configurationLimitBackDays);
        verify(configurationRepository, times(1)).save(configurationLimitForwardDays);
    }

    private Optional<Country> createCountry() {
        Country country = Country.builder()
                .id(UUID.randomUUID())
                .code("BR")
                .name("Brasil")
                .acronym("BRZ")
                .build();

        return Optional.of(country);
    }

    private ConfigurationCountryRequestDTO createConfigurationCountryRequestDTO() {
        return ConfigurationCountryRequestDTO.builder()
                .country("Brasil")
                .build();
    }

    private ConfigurationLimitYearRequestDTO createConfigurationLimitYearRequestDTO() {
        return ConfigurationLimitYearRequestDTO.builder()
                .numberBackYears(10)
                .numberForwardYears(25)
                .build();
    }

    private Configuration createConfiguration(final Configurationkey key, final String value, final String defaultValue) {
        return Configuration.builder()
                .key(key)
                .value(value)
                .defaultValue(defaultValue)
                .build();
    }

}

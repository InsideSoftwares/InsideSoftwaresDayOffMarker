package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationCountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationLimitYearRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.ConfigurationNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Configuration;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.repository.ConfigurationRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.ConfigurationSearchService;
import br.com.insidesoftwares.dayoffmarker.specification.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class ConfigurationServiceBean implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;
    private final CountryRepository countryRepository;
    private final ConfigurationSearchService configurationSearchService;

    @InsideAudit(description = "Update configuration")
    @Transactional(rollbackFor = ConfigurationNotExistException.class)
    @Override
    public void updateConfiguration(final Configurationkey configurationKey, String value) {
        log.info("Updating configuration: {}", configurationKey);
        Configuration configuration = configurationSearchService.findConfigurationByKey(configurationKey);
        configuration.setValue(value);
        configurationRepository.save(configuration);
    }

    @InsideAudit(description = "Configure limit of previous and subsequent years")
    @Transactional(rollbackFor = {
            ConfigurationNotExistException.class,
            Exception.class
    })
    @Override
    public void configurationLimitYear(final ConfigurationLimitYearRequestDTO configurationLimitYearRequestDTO) {
        log.info("Updating configuration Limit Back and Forward days years");
        updateConfiguration(Configurationkey.LIMIT_BACK_DAYS_YEARS, configurationLimitYearRequestDTO.numberBackYears().toString());
        updateConfiguration(Configurationkey.LIMIT_FORWARD_DAYS_YEARS, configurationLimitYearRequestDTO.numberForwardYears().toString());
    }

    @InsideAudit(description = "Configure system default parents")
    @Transactional(rollbackFor = {
            ConfigurationNotExistException.class,
            CountryNameInvalidException.class,
            Exception.class
    })
    @Override
    public void configurationCountry(final ConfigurationCountryRequestDTO configurationCountryRequestDTO) {
        log.info("Updating configuration country default");
        Optional<Country> countryOptional = countryRepository.findCountryByName(configurationCountryRequestDTO.country());
        String countryName = countryOptional.orElseThrow(CountryNameInvalidException::new).getName();

        updateConfiguration(Configurationkey.COUNTRY_DEFAULT, countryName);
    }

}

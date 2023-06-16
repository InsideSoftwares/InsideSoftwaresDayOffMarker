package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationCountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationLimitYearRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.ConfigurationNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.entity.Configuration;
import br.com.insidesoftwares.dayoffmarker.entity.Country;
import br.com.insidesoftwares.dayoffmarker.repository.ConfigurationRepository;
import br.com.insidesoftwares.dayoffmarker.repository.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ConfigurationServiceBean implements ConfigurationService {

	private final ConfigurationRepository configurationRepository;
	private final CountryRepository countryRepository;

	@Override
    public Configuration findConfigurationByKey(final Configurationkey configurationKey) {
        Optional<Configuration> configurationOptional = configurationRepository.findConfigurationByKey(configurationKey);
        if(configurationOptional.isPresent()){
            return configurationOptional.get();
        } else {
            throw new ConfigurationNotExistException();
        }
    }

	@Override
    public String findValueConfigurationByKey(final Configurationkey configurationKey) {
        Optional<Configuration> configurationOptional = configurationRepository.findConfigurationByKey(configurationKey);
        if(configurationOptional.isPresent()){
            Configuration configuration = configurationOptional.get();
            return configuration.getValueOrDefaulValue();
        } else {
            throw new ConfigurationNotExistException();
        }
    }

	@Transactional(rollbackFor = ConfigurationNotExistException.class)
	@Override
	public void updateConfiguration(final Configurationkey configurationKey, String value) {
		Configuration configuration = findConfigurationByKey(configurationKey);
		configuration.setValue(value);
		configurationRepository.save(configuration);
	}

	@Transactional(rollbackFor = {
		ConfigurationNotExistException.class,
		Exception.class
	})
	@Override
	public void configurationLimitYear(final ConfigurationLimitYearRequestDTO configurationLimitYearRequestDTO) {
		updateConfiguration(Configurationkey.LIMIT_BACK_DAYS_YEARS, configurationLimitYearRequestDTO.numberBackYears().toString());
		updateConfiguration(Configurationkey.LIMIT_FORWARD_DAYS_YEARS, configurationLimitYearRequestDTO.numberForwardYears().toString());
	}

	@Transactional(rollbackFor = {
		ConfigurationNotExistException.class,
		CountryNameInvalidException.class,
		Exception.class
	})
	@Override
	public void configurationCountry(final ConfigurationCountryRequestDTO configurationCountryRequestDTO) {
		Optional<Country> countryOptional = countryRepository.findCountryByName(configurationCountryRequestDTO.country());
		String countryName = countryOptional.orElseThrow(CountryNameInvalidException::new).getName();

		updateConfiguration(Configurationkey.COUNTRY_DEFAULT, countryName);
	}

}

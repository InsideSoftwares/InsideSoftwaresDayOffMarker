package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.enums.eConfigurationkey;
import br.com.sawcunha.dayoffmarker.commons.exception.error.ConfigurationNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.entity.Configuration;
import br.com.sawcunha.dayoffmarker.repository.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ConfigurationServiceBean {

    private final ConfigurationRepository configurationRepository;

    private final static String INIT_TRUE = "S";

    public static String getInitTrueStatus(){
        return INIT_TRUE;
    }

    @Transactional(readOnly = true)
    public Configuration findConfigurationByKey(final eConfigurationkey configurationkey) throws ConfigurationNotExistException {
        Optional<Configuration> configurationOptional = configurationRepository.findConfigurationByKey(configurationkey);
        if(configurationOptional.isPresent()){
            return configurationOptional.get();
        } else {
            throw new ConfigurationNotExistException();
        }
    }

    @Transactional(readOnly = true)
    public String findValueConfigurationByKey(final eConfigurationkey configurationkey) throws ConfigurationNotExistException {
        Optional<Configuration> configurationOptional = configurationRepository.findConfigurationByKey(configurationkey);
        if(configurationOptional.isPresent()){
            Configuration configuration = configurationOptional.get();
            return configuration.getValueOrDefaulValue();
        } else {
            throw new ConfigurationNotExistException();
        }
    }

    @Transactional(rollbackFor = ConfigurationNotExistException.class)
    public void updateConfiguration(final eConfigurationkey configurationkey, String value) throws DayOffMarkerGenericException {
        Configuration configuration = findConfigurationByKey(configurationkey);
        configuration.setValue(value);
        configurationRepository.save(configuration);
    }

    @Transactional(readOnly = true)
    public boolean isInitializedApplication() throws DayOffMarkerGenericException {
        Configuration configuration = findConfigurationByKey(eConfigurationkey.INITIAL_CONFIGURATION);
        return Objects.nonNull(configuration.getValue()) && configuration.getValue().equals(INIT_TRUE);
    }



}

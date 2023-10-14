package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.ConfigurationNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Configuration;
import br.com.insidesoftwares.dayoffmarker.domain.repository.ConfigurationRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.ConfigurationSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class ConfigurationSearchServiceBean implements ConfigurationSearchService {

    private final ConfigurationRepository configurationRepository;

    @InsideAudit(description = "Search configuration by Key")
    @Override
    public Configuration findConfigurationByKey(final Configurationkey configurationKey) {
        log.info("Find configuration by key: {}", configurationKey.name());
        Optional<Configuration> configurationOptional = configurationRepository.findConfigurationByKey(configurationKey);
        return configurationOptional.orElseThrow(ConfigurationNotExistException::new);
    }

    @InsideAudit(description = "Search configuration value by Key")
    @Override
    public String findValueConfigurationByKey(final Configurationkey configurationKey) {
        log.info("Find valeu or default value configuration by key: {}", configurationKey.name());
        Configuration configuration = findConfigurationByKey(configurationKey);
        return configuration.getValueOrDefaulValue();
    }
}

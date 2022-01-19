package br.com.sawcunha.dayoffmarker.repository;

import br.com.sawcunha.dayoffmarker.commons.enums.eConfigurationkey;
import br.com.sawcunha.dayoffmarker.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    @Query("""
        SELECT configuration.key
        FROM Configuration configuration
        WHERE
            configuration.value = :value OR
            ( configuration.value IS NULL AND configuration.defaultValue = :value)
    """)
    eConfigurationkey findConfigurationKeyByValue(String value);

    Optional<Configuration> findConfigurationByKey(eConfigurationkey key);

}

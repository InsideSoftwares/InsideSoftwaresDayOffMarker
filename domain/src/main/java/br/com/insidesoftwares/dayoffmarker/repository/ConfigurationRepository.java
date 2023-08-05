package br.com.insidesoftwares.dayoffmarker.repository;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.entity.Configuration;
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
    Configurationkey findConfigurationKeyByValue(String value);

    Optional<Configuration> findConfigurationByKey(Configurationkey key);

}

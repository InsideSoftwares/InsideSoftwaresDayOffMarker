package br.com.insidesoftwares.dayoffmarker.configuration.application;

import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "DayOffMarkerEntityManagerFactory",
        transactionManagerRef = "DayOffMarkerTransactionManager",
        basePackages = {"br.com.insidesoftwares.dayoffmarker.domain.repository"})
@EnableConfigurationProperties(LiquibaseProperties.class)
public class DayOffMarkerDataSourceConfiguration {

    @Primary
    @Bean(name = "DayOffMarkerDataSourcePropos")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dayOffMarkerDataSourcePropos() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "DayOffMarkerDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dayOffMarkerDataSource(@Qualifier("DayOffMarkerDataSourcePropos") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "DayOffMarkerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dayOffMarkerEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("DayOffMarkerDataSource") DataSource dataSource
    ) {
        return builder.dataSource(dataSource)
                .packages("br.com.insidesoftwares.dayoffmarker.domain.entity")
                .persistenceUnit("DayOffMarkerPersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "DayOffMarkerTransactionManager")
    @ConfigurationProperties("spring.jpa")
    public PlatformTransactionManager dayOffMarkerTransactionManager(
            @Qualifier("DayOffMarkerEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @ConditionalOnProperty(prefix="spring.liquibase", name = "enable", havingValue = "true")
    @Bean("DayOffMarkerSpringLiquibase")
    @Primary
    @ConfigurationProperties(prefix = "spring.liquibase", ignoreUnknownFields = false)
    public SpringLiquibase springLiquibase(@Qualifier("DayOffMarkerDataSource") DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(false);
        return liquibase;
    }
}

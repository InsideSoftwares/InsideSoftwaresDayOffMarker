package br.com.insidesoftwares.dayoffmarker.job.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableJpaRepositories(basePackages = {"br.com.insidesoftwares.dayoffmarker.repository"})
@EntityScan(basePackages = "br.com.insidesoftwares.dayoffmarker.entity")
public class ScheduledConfiguration {
}

package br.com.insidesoftwares.dayoffmarker.job;

import br.com.insidesoftwares.dayoffmarker.service.listener.DayOffMarkerStartupListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableDiscoveryClient
@AutoConfigurationPackage
@ComponentScan(basePackages = {"br.com.insidesoftwares.dayoffmarker.job.configuration", "br.com.insidesoftwares"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DayOffMarkerStartupListener.class)
}, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DataSourceAutoConfiguration.class)
})
@SpringBootApplication(exclude = {RedisAutoConfiguration.class, LiquibaseAutoConfiguration.class})
public class DayOffMarkerJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayOffMarkerJobApplication.class, args);
    }

}

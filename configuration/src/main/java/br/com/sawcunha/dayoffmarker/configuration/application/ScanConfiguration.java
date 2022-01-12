package br.com.sawcunha.dayoffmarker.configuration.application;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages ={"br.com.sawcunha.dayoffmarker"})
@ComponentScan(basePackages={"br.com.sawcunha.dayoffmarker"})
public class ScanConfiguration {
}

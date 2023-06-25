package br.com.insidesoftwares.dayoffmarker.configuration.application;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ConfigurationPropertiesScan(basePackages ={"br.com.insidesoftwares.dayoffmarker", "br.com.insidesoftwares.commons"})
@ComponentScan(
	basePackages= {
		"br.com.insidesoftwares.dayoffmarker",
		"br.com.insidesoftwares.commons",
		"br.com.insidesoftwares.execption"
	}
)
@EnableAspectJAutoProxy
public class ScanConfiguration {
}

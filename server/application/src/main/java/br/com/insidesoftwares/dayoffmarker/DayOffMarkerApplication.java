package br.com.insidesoftwares.dayoffmarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@AutoConfigurationPackage
@ComponentScan(basePackages={"br.com.insidesoftwares.dayoffmarker.configuration", "br.com.insidesoftwares"})
@SpringBootApplication( exclude = { RedisAutoConfiguration.class, DataSourceAutoConfiguration.class } )
public class DayOffMarkerApplication {
	public static void main(String[] args) {
		SpringApplication.run(DayOffMarkerApplication.class, args);
	}

}

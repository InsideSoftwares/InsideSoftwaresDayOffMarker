package br.com.insidesoftwares.dayoffmarker.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@AutoConfigurationPackage
@ComponentScan(basePackages={"br.com.insidesoftwares"})
@SpringBootApplication( exclude = { RedisAutoConfiguration.class } )
public class DayOffMarkerJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayOffMarkerJobApplication.class, args);
    }

}

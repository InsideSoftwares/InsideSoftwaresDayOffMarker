package br.com.sawcunha.dayoffmarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"br.com.sawcunha.dayoffmarker.configuration"})
@AutoConfigurationPackage
public class DayOffMarkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayOffMarkerApplication.class, args);
    }

}

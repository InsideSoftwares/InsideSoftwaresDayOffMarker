package br.com.sawcunha.dayoffmarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
@ComponentScan(basePackages={"br.com.sawcunha.dayoffmarker.configuration"})
@AutoConfigurationPackage
public class DayOffMarkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayOffMarkerApplication.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(10); //reload messages every 10 seconds
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

}

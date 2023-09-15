package br.com.insidesoftwares.dayoffmarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication( exclude = { RedisAutoConfiguration.class } )
@SpringBootConfiguration
@ComponentScan({"*ignore*", "br.com.insidesoftwares.commons.configuration.mysql"})
public class RepositoryTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepositoryTestApplication.class, args);
	}

}

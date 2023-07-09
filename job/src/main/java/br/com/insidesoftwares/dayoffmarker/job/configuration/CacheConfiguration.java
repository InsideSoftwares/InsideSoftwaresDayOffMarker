package br.com.insidesoftwares.dayoffmarker.job.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ConditionalOnProperty(
	prefix="spring.cache", name = "enable",
	havingValue = "true",
	matchIfMissing = true)
@Import({RedisAutoConfiguration.class})
@Configuration
public class CacheConfiguration {
}

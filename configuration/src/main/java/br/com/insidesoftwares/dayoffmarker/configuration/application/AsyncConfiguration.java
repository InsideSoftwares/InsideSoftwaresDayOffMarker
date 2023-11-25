package br.com.insidesoftwares.dayoffmarker.configuration.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration(proxyBeanMethods = false)
@EnableAsync
public final class AsyncConfiguration {

}

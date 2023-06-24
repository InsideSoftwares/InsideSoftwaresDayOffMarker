package br.com.insidesoftwares.dayoffmarker.configuration.application.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@ComponentScan(basePackages = {
	"br.com.insidesoftwares.authenticator.controller"
})
@RequiredArgsConstructor
public class SecurityConfiguration {}

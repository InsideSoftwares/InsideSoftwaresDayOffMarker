package br.com.insidesoftwares.dayoffmarker.configuration.application;

import br.com.insidesoftwares.securitycommons.WebSecurityConfig;
import br.com.insidesoftwares.securitycommons.exception.AccessDeniedExceptionHandler;
import br.com.insidesoftwares.securitycommons.filter.CorsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@ComponentScan(basePackages = {
	"br.com.insidesoftwares.authenticator.controller"
})
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;
	private final CorsFilter corsFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return WebSecurityConfig.securityFilterChain(
			httpSecurity, accessDeniedExceptionHandler, corsFilter
		);
	}

	@Bean
	public GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return WebSecurityConfig.grantedAuthorityDefaults();
	}

}

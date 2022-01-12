package br.com.sawcunha.dayoffmarker.security;

import br.com.sawcunha.dayoffmarker.security.exception.AccessDeniedExceptionHandler;
import br.com.sawcunha.dayoffmarker.security.jwt.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = { "br.com.sawcunha.dayoffmarker.controller" })
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(auth -> auth
                        .antMatchers("/**").permitAll()
                        .antMatchers("/api/**").denyAll()
                        .antMatchers("/api/authentication/**").permitAll()
                        .anyRequest().authenticated())
                .csrf()
                .disable()
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
               .exceptionHandling().accessDeniedHandler(accessDeniedExceptionHandler);
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.sessionManagement() // dont create a session for this configuration
                .sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

}

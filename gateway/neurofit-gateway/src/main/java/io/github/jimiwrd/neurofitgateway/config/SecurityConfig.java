package io.github.jimiwrd.neurofitgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                                .pathMatchers("actuator/health/**").permitAll()
                                .pathMatchers("/register", "/login").permitAll()
                                .pathMatchers("/realms/neurofit/**").permitAll()
                                .anyExchange()
                                .authenticated()
                        )
                .oauth2ResourceServer(auth -> auth.jwt(Customizer.withDefaults()))
                .build();
    }
}

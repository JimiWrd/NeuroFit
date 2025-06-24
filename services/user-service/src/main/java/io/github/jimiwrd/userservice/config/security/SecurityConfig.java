package io.github.jimiwrd.userservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@SuppressWarnings("unused")
public class SecurityConfig {

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthEntryPoint entryPoint, Converter<Jwt, AbstractAuthenticationToken> authenticationConverter) throws Exception {
        http.oauth2ResourceServer(resourceServer -> resourceServer
                .jwt(jwtDecoder -> jwtDecoder.jwtAuthenticationConverter(authenticationConverter))
                .authenticationEntryPoint(entryPoint));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/users")
                .authenticated()
                .anyRequest()
                .permitAll());
//        .exceptionHandling(exception -> exception.authenticationEntryPoint(entryPoint));

        return http.build();
    }

}

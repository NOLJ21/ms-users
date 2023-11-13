package com.parqueo.usuarios.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class GlobalSecurityConfiguration
{
    private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;

    public GlobalSecurityConfiguration(TokenConverterProperties properties)
    {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        this.keycloakJwtTokenConverter =
                new KeycloakJwtTokenConverter(jwtGrantedAuthoritiesConverter, properties);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( authorizeHttpRequests -> {
            authorizeHttpRequests
                    .requestMatchers("/api/v1/users/all").hasRole("ADMIN")
                    .requestMatchers("/api/v1/users/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/v1/users/login").hasRole("USER")
                    .requestMatchers("/api/v1/users/save").hasRole("ADMIN")
                    .requestMatchers("/api/v1/users/delete/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/v1/persons/all").hasRole("ADMIN")
                    .requestMatchers("/api/v1/persons/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/v1/persons/login").hasRole("USER")
                    .requestMatchers("/api/v1/persons/save").hasRole("ADMIN")
                    .requestMatchers("/api/v1/persons/delete/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/v1/customer/all").hasRole("ADMIN")
                    .requestMatchers("/api/v1/customer/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/v1/customer/login").hasRole("USER")
                    .requestMatchers("/api/v1/customer/save").hasRole("ADMIN")
                    .requestMatchers("/api/v1/customer/delete/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/v1/admin/all").hasRole("ADMIN")
                    .requestMatchers("/api/v1/admin/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/v1/admin/login").hasRole("USER")
                    .requestMatchers("/api/v1/admin/save").hasRole("ADMIN")
                    .requestMatchers("/api/v1/admin/delete/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/v1/employee/all").hasRole("ADMIN")
                    .requestMatchers("/api/v1/employee/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/v1/employee/login").hasRole("USER")
                    .requestMatchers("/api/v1/employee/save").hasRole("ADMIN")
                    .requestMatchers("/api/v1/employee/delete/{id}").hasRole("ADMIN")
                    .anyRequest().authenticated();
        });
        http.oauth2ResourceServer( (oauth2) -> {
            oauth2.jwt( (jwt) -> {
                jwt.jwtAuthenticationConverter(keycloakJwtTokenConverter);
            });
        });

        http.sessionManagement((session) -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(java.util.Arrays.asList("*"));
        //configuration.setAllowedOrigins(java.util.Arrays.asList( "http://localhost:4200", "http://host.docker.internal:4200"));
        configuration.setAllowedMethods(java.util.Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(java.util.Arrays.asList("*"));
        configuration.setExposedHeaders(java.util.Arrays.asList("Access-Control-Allow-Origin"));
        configuration.setAllowCredentials(true);
        configuration.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }



}
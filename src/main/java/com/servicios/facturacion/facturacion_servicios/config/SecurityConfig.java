package com.servicios.facturacion.facturacion_servicios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.servicios.facturacion.facturacion_servicios.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAutenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            return http
                    .csrf(csrf -> csrf
                            .disable())
                    .authorizeHttpRequests(authRequests -> authRequests
                            .requestMatchers("/auth/login").permitAll()
                            .requestMatchers("/auth/register").hasAuthority("admin")
                            .requestMatchers("/auth/{username}").hasAuthority("admin")
                            .requestMatchers("/auth").permitAll()
                            
                            .requestMatchers(HttpMethod.POST, "/api/client/**").hasAnyAuthority("admin", "cajero")
                            .requestMatchers(HttpMethod.PUT, "/api/client/**").hasAuthority("admin")
                            .requestMatchers(HttpMethod.DELETE, "/api/client/**").hasAuthority("admin")
                            .requestMatchers(HttpMethod.GET, "/api/client/**").hasAnyAuthority("admin", "cajero")

                            .requestMatchers("/api/productos/**").permitAll()
                            .requestMatchers("/api/category/**").permitAll()


                            .requestMatchers("/api/sales/**").permitAll()
                            .requestMatchers("/api/sales").permitAll()
                            .anyRequest().authenticated())
                    .sessionManagement(sessionManagement -> 
                    sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authenticationProvider(authProvider)
                    .addFilterBefore(jwtAutenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

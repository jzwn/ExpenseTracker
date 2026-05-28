package com.ExpenseTracker.ExpenseTracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(
            JwtFilter jwtFilter) {

        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                // DISABLE CSRF
                .csrf(csrf -> csrf.disable())

                // DISABLE FORM LOGIN
                .formLogin(form -> form.disable())

                // DISABLE HTTP BASIC
                .httpBasic(basic -> basic.disable())

                // ROUTES
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC ROUTES
                        .requestMatchers(
                                "/api/auth/**")
                        .permitAll()

                        // EVERYTHING ELSE PROTECTED
                        .anyRequest()
                        .authenticated())

                // STATELESS SESSION
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                // ADD JWT FILTER
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
package com.ExpenseTracker.ExpenseTracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(
            JwtService jwtService) {

        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain)

            throws ServletException, IOException {

        // GET PATH
        String path =
                request.getServletPath();

        // SKIP LOGIN & REGISTER
        if (path.startsWith("/api/auth")) {

            filterChain.doFilter(
                    request,
                    response);

            return;
        }

        // GET AUTH HEADER
        final String authHeader =
                request.getHeader("Authorization");

        // NO TOKEN
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        // REMOVE "Bearer "
        String token =
                authHeader.substring(7);

        try {

            // EXTRACT EMAIL
            String email =
                    jwtService.extractEmail(token);

            System.out.println(
                    "Authenticated User: "
                            + email);

        } catch (Exception e) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        filterChain.doFilter(
                request,
                response);
    }
}
package com.ExpenseTracker.ExpenseTracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // SECRET KEY
    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey1234";

    // GENERATE TOKEN
    public String generateToken(
            String email) {

        return Jwts.builder()

                .subject(email)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000L * 60 * 60 * 24))

                .signWith(
                        Keys.hmacShaKeyFor(
                                SECRET_KEY.getBytes(StandardCharsets.UTF_8)),
                        Jwts.SIG.HS256)

                .compact();
    }

    // EXTRACT EMAIL
    public String extractEmail(
            String token) {

        return extractClaim(
                token,
                Claims::getSubject);
    }

    // EXTRACT CLAIM
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        final Claims claims =
                extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    // EXTRACT ALL CLAIMS
    private Claims extractAllClaims(
            String token) {

        return Jwts.parser()

                .verifyWith(
                        Keys.hmacShaKeyFor(
                                SECRET_KEY.getBytes(StandardCharsets.UTF_8)))

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }

    // VALIDATE TOKEN
    public boolean isTokenValid(
            String token,
            String email) {

        final String extractedEmail =
                extractEmail(token);

        return extractedEmail.equals(email);
    }
}

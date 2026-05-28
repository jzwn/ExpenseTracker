package com.ExpenseTracker.ExpenseTracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // SECRET KEY
    private static final String SECRET_KEY =
            "mysecretkeymysecretkey";

    // GENERATE TOKEN
    public String generateToken(
            String email) {

        return Jwts.builder()

                .setSubject(email)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60 * 24))

                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY)

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

                .setSigningKey(SECRET_KEY)

                .parseClaimsJws(token)

                .getBody();
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

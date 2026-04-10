package com.authService.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${JWT_SECRET:my-super-secret-key-that-is-at-least-32-characters-long!!}")
    private String jwtSecret;

    @Value("${JWT_EXPIRATION:86400000}")
    private long jwtExpiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String username, String role, String department) {
        log.info("Generating JWT token for user: {}, role: {}, department: {}", username, role, department);
        
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("department", department)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String username, String role) {
        return generateToken(username, role, "");
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public String extractDepartment(String token) {
        return getClaims(token).get("department", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
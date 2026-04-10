package com.apiGateway.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    @Value("${JWT_SECRET:my-super-secret-key-that-is-at-least-32-characters-long!!}")
    private String jwtSecret;

    public Claims extractClaims(String token) {
        log.debug("Extracting claims from token");
        return Jwts.parser()
                .verifyWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public String extractDepartment(String token) {
        return extractClaims(token).get("department", String.class);
    }
}
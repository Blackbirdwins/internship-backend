package com.internship.student.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key = Keys.hmacShaKeyFor(
            "mySecretKeyForStudentManagementSystemThatIsAtLeast32BytesLong123456".getBytes());

    private final long EXPIRATION_MS = 86400000;

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            String extracted = extractUsername(token);
            return extracted.equals(username);
        } catch (Exception e) {
            return false;
        }
    }
}
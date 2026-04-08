package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.config.JwtProperties;
import com.jarol.auth.auth_api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateAccessToken(User user, UUID sessionId) {
        return buildToken(user, sessionId, jwtProperties.getAccessExpiration());
    }

    public String generateRefreshToken(User user, UUID sessionId) {
        return buildToken(user, sessionId, jwtProperties.getRefreshExpiration());
    }

    private String buildToken(User user, UUID sessionId, long expiration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("sessionId", sessionId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        return key;
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return username.equals(user.getEmail()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

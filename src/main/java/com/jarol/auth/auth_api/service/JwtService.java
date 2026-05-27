package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.config.JwtProperties;
import com.jarol.auth.auth_api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HexFormat;
import java.util.List;
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
        return buildToken(user, sessionId, jwtProperties.getAccessExpiration(), jwtProperties.getAccessType());
    }

    public String generateRefreshToken(User user, UUID sessionId) {
        return buildToken(user, sessionId, jwtProperties.getRefreshExpiration(), jwtProperties.getRefreshType());
    }

    private String buildToken(User user, UUID sessionId, long expiration, String type) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("sessionId", sessionId.toString())
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles().stream().map(role -> "ROLE_" + role.getName().name()).toList())
                .claim("type", type)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        return key;
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(parseAndValidateToken(token).getSubject());
    }

    public UUID extractSessionId(String token) {
        return UUID.fromString(parseAndValidateToken(token).get("sessionId", String.class));
    }

    public String extractEmail(String token) {
        return parseAndValidateToken(token).get("email", String.class);
    }

    public String extractUsername(String token) {
        return parseAndValidateToken(token).get("username", String.class);
    }

    public List<String> extractRoles(String token) {
        return parseAndValidateToken(token).get("roles", List.class);
    }


    public Claims parseAndValidateToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}

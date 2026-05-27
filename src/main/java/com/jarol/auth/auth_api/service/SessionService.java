package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.config.JwtProperties;
import com.jarol.auth.auth_api.dto.response.AuthResponse;
import com.jarol.auth.auth_api.exception.InvalidCredentialsException;
import com.jarol.auth.auth_api.exception.InvalidTokenException;
import com.jarol.auth.auth_api.exception.SessionNotFoundException;
import com.jarol.auth.auth_api.mapper.IAuthMapper;
import com.jarol.auth.auth_api.model.Session;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.valueObject.SessionMetadata;
import com.jarol.auth.auth_api.repository.ISessionRepository;
import com.jarol.auth.auth_api.service.parser.UserAgentParser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {
    private final JwtProperties jwtProperties;
    private final UserAgentParser userAgentParser;
    private final ISessionRepository sessionRepository;
    private final JwtService jwtService;
    private final IAuthMapper authMapper;

    @Override
    public AuthResponse createSessionAndTokens(User user, String userAgent, String ip) {
        SessionMetadata metadata = userAgentParser.parse(userAgent, ip);

        Instant expiresAt = Instant.now().plusMillis(jwtProperties.getRefreshExpiration());

        UUID sessionId = UUID.randomUUID();

        String accessToken = jwtService.generateAccessToken(user, sessionId);
        String refreshToken = jwtService.generateRefreshToken(user, sessionId);

        String hash = hashRefreshToken(refreshToken);

        Session session = Session.builder()
                .id(sessionId)
                .user(user)
                .refreshTokenHash(hash)
                .expiresAt(expiresAt)
                .ipAddress(metadata.getIpAddress())
                .userAgent(metadata.getUserAgent())
                .deviceType(metadata.getDeviceType())
                .os(metadata.getOs())
                .browser(metadata.getBrowser())
                .revoked(false)
                .build();

        sessionRepository.save(session);

        return authMapper.userToAuthResponse(user, accessToken, refreshToken, expiresAt);
    }

    @Override
    public void revokeSessionBySessionId(UUID sessionId) {
        Session session = getSessionBySessionId(sessionId);
        if(session.isRevoked()){
            return;
        }
        session.setRevoked(true);
        session.setRevokedAt(Instant.now());
        sessionRepository.save(session);
    }

    @Override
    public int revokeAllSessionsByUserId(UUID userId) {
        return sessionRepository.revokeAllSessionByUserId(userId, Instant.now());
    }

    @Override
    public Session getSessionBySessionId(UUID sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new SessionNotFoundException()
        );
    }


    private String hashRefreshToken(String refreshToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);

        }
    }
}

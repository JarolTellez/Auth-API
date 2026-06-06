package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.config.JwtProperties;
import com.jarol.auth.auth_api.dto.response.AuthResponse;
import com.jarol.auth.auth_api.dto.response.RevokeAllSessionsResponse;
import com.jarol.auth.auth_api.dto.response.SessionResponse;
import com.jarol.auth.auth_api.dto.response.SessionsResponse;
import com.jarol.auth.auth_api.exception.AccessDeniedException;
import com.jarol.auth.auth_api.exception.SessionNotFoundException;
import com.jarol.auth.auth_api.mapper.IAuthMapper;
import com.jarol.auth.auth_api.mapper.ISessionMapper;
import com.jarol.auth.auth_api.model.Session;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.valueObject.SessionMetadata;
import com.jarol.auth.auth_api.repository.ISessionRepository;
import com.jarol.auth.auth_api.service.parser.UserAgentParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {
    private final JwtProperties jwtProperties;
    private final UserAgentParser userAgentParser;
    private final ISessionRepository sessionRepository;
    private final JwtService jwtService;
    private final IAuthMapper authMapper;
    private final ISessionMapper iSessionMapper;

    @Override
    public AuthResponse createSessionAndTokens(User user, String userAgent, String ip) {
        SessionMetadata metadata = userAgentParser.parse(userAgent, ip);

        Instant expiresAt = Instant.now().plusMillis(jwtProperties.getRefreshExpiration());

        UUID sessionId = UUID.randomUUID();

        String accessToken = jwtService.generateAccessToken(user, sessionId);
        String refreshToken = jwtService.generateRefreshToken(user, sessionId);

        String hash = jwtService.hashRefreshToken(refreshToken);

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

        save(session);

        return authMapper.userToAuthResponse(user, accessToken, refreshToken, jwtProperties.getRefreshExpiration());
    }


    @Override
    public Session getSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new SessionNotFoundException()
        );
    }

    @Override
    public Session save(Session session) {
       return sessionRepository.save(session);
    }

    @Override
    public SessionsResponse getActiveSessions(UUID userId, UUID currentSessionId) {
        List<Session> sessions =sessionRepository.findByUserIdAndRevokedFalse(userId);
        List<SessionResponse> responseSessions=iSessionMapper.sessionsToSessionsResponse(sessions, currentSessionId);

        return new SessionsResponse(responseSessions,responseSessions.size());


    }

    @Override
    public RevokeAllSessionsResponse revokeAllSessions(UUID userId) {
        int revokedSessions = sessionRepository.revokeAllSessionByUserId(userId, Instant.now());
        ;

        return new RevokeAllSessionsResponse(revokedSessions);
    }

    @Override
    public void revokeSession(UUID sessionId, UUID userId) {
        Session session = getSessionById(sessionId);

        if (!session.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to revoke this session");
        }
        if (session.isRevoked()) {
            return;
        }
        session.setRevoked(true);
        session.setRevokedAt(Instant.now());
        sessionRepository.save(session);
    }


}

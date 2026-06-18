package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.response.*;
import com.jarol.auth.auth_api.model.Session;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.valueObject.SessionMetadata;

import java.util.List;
import java.util.UUID;


public interface ISessionService {

    AuthResponse createSessionAndTokens(User user, String userAgent, String ip);

    void revokeSession(UUID sessionId, UUID userId);

    Session getSessionById(UUID sessionId);

    Session save(Session session);

    SessionsResponse getActiveSessions(UUID userId, UUID currentSessionId);

    RevokeAllSessionsResponse revokeAllSessions(UUID userId);

}

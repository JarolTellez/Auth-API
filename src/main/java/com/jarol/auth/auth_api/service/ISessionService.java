package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.response.AuthResponse;
import com.jarol.auth.auth_api.dto.response.RevokeAllSessionsResponse;
import com.jarol.auth.auth_api.dto.response.SessionResponse;
import com.jarol.auth.auth_api.dto.response.SessionsResponse;
import com.jarol.auth.auth_api.model.Session;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.valueObject.SessionMetadata;

import java.util.List;
import java.util.UUID;


public interface ISessionService {

    AuthResponse createSessionAndTokens(User user, String userAgent, String ip);

    void revokeSession(UUID sessionId, UUID userId);

    Session getSessionBySessionId(UUID sessionId);

    SessionsResponse getActiveSessions(UUID userId, UUID currentSessionId);

    RevokeAllSessionsResponse revokeAllSessions(UUID userId);

}

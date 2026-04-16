package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.model.Session;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.valueObject.SessionMetadata;

public interface ISessionService {

    Session createSession(User user, String refreshTokenHash, SessionMetadata metadata);
}

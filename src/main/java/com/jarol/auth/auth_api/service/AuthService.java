package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.config.JwtProperties;
import com.jarol.auth.auth_api.dto.request.LoginRequest;
import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.response.AuthResponse;
import com.jarol.auth.auth_api.dto.response.UserResponse;
import com.jarol.auth.auth_api.exception.InvalidCredentialsException;
import com.jarol.auth.auth_api.mapper.IAuthMapper;
import com.jarol.auth.auth_api.mapper.IUserMapper;
import com.jarol.auth.auth_api.model.Role;
import com.jarol.auth.auth_api.model.Session;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.enums.EnumRole;
import com.jarol.auth.auth_api.model.valueObject.SessionMetadata;
import com.jarol.auth.auth_api.repository.IRoleRepository;
import com.jarol.auth.auth_api.repository.ISessionRepository;
import com.jarol.auth.auth_api.repository.IUserRepository;
import com.jarol.auth.auth_api.service.parser.UserAgentParser;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IUserService userService;
    private final ISessionService sessionService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    @Override
    public AuthResponse register(RegisterRequest request, HttpServletRequest httpRequest) {

        String userAgent = httpRequest.getHeader("User-Agent");
        String ip = extractIp(httpRequest);

        User user = userService.createUser(request);

        return  sessionService.createSessionAndTokens(user, userAgent, ip);
    }

    @Override
    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        String userAgent = httpRequest.getHeader("User-Agent");
        String ip = extractIp(httpRequest);
        User user = userService.getUserByIdentifier(request.getIdentifier());

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new InvalidCredentialsException();
        }

        return sessionService.createSessionAndTokens(user, userAgent, ip);
    }

    @Override
    public void logout(UUID sessionId) {

        sessionService.revokeSessionBySessionId(sessionId);

    }


    private String extractIp(HttpServletRequest httpRequest) {
        String ip = httpRequest.getHeader("X-Forwarded-For");

        if (ip != null && !ip.isBlank()) {
            return ip.split(",")[0].trim();
        }

        return httpRequest.getRemoteAddr();

    }


}

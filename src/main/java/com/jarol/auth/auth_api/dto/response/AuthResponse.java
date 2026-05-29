package com.jarol.auth.auth_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;


public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Instant refreshTokenExpiresAt,
        UserResponse user
) {

    public AuthResponse(
            String accessToken,
            String refreshToken,
            Instant refreshTokenExpiresAt,
            UserResponse user
    ) {
        this(
                accessToken,
                refreshToken,
                "Bearer",
                refreshTokenExpiresAt,
                user
        );
    }
}
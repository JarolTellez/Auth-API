package com.jarol.auth.auth_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiresAt;
    private UserResponse user;
}

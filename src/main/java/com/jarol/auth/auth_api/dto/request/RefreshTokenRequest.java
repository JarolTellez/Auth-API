package com.jarol.auth.auth_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Data
public class RefreshTokenRequest {
    @NotBlank
    private Instant refreshToken;
}

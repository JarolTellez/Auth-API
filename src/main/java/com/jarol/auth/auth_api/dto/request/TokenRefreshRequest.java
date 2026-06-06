package com.jarol.auth.auth_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(

        @NotBlank(message="RefreshToken is required")
        String refreshToken
) {
}

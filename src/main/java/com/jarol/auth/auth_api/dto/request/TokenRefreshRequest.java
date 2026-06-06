package com.jarol.auth.auth_api.dto.request;

public record TokenRefreshRequest(
        String refreshToken
) {
}

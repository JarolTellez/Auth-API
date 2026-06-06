package com.jarol.auth.auth_api.dto.response;

public record TokenRefreshResponse(
        String accessToken,
        String tokenType,
        Long expiresIn
) {


    public TokenRefreshResponse(String accessToken, Long expiresIn){
        this(
                accessToken,
                "Bearer",
                expiresIn
        );
    }
}

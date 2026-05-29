package com.jarol.auth.auth_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record LogoutRequest (
        @NotBlank(message="RefreshToken is required")
        String refreshToken
) {

}

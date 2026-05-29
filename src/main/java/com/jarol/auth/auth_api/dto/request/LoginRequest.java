package com.jarol.auth.auth_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


public record LoginRequest(
        @NotBlank(message = "Identifier is required")
        @Size(min = 6, max = 25)
        String identifier,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 50)
        String password
) {


}

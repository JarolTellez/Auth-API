package com.jarol.auth.auth_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


public record RegisterRequest(

        @NotBlank
        @Size(min = 6, max = 25, message = "Username must be between 6 and 25 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9._]+$",
                message = "Username can only contain letters, numbers, dots and underscores"
        )
        String username,

        @NotBlank
        @Email(message = "Email format is invalid")
        @Size(max = 150, message = "Email cannot exceed 150 characters")
        String email,

        @NotBlank
        @Size(min = 8, max = 255)
        String password
) {

}

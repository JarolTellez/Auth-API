package com.jarol.auth.auth_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "password")
public class LoginRequest {

    @NotBlank(message = "Identifier is required")
    @Size(max = 255)
    private String identifier;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50)
    private String password;


}

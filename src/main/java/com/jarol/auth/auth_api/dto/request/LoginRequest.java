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

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    @Size(max = 255)
    private String deviceInfo;

    @Size(max = 39)
    private String ipAddress;

    @Size(max = 512)
    private String userAgent;

}

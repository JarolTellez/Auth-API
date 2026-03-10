package com.jarol.auth.auth_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @Size(max=50)
    private String name;

    @Email
    @Size
    private String email;

    @Size(min = 8, max = 50)
    private String password;
}

package com.jarol.auth.auth_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

public record UpdateProfileRequest(

        @Size(max = 50)
        String name,

        @Email
        @Size
        String email,

        @Size(min = 8, max = 50)
        String password
) {


}

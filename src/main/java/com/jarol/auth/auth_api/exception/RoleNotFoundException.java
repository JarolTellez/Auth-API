package com.jarol.auth.auth_api.exception;

import com.jarol.auth.auth_api.model.enums.EnumRole;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends BussinesException {

    protected RoleNotFoundException(EnumRole role) {
        super(
                "Role not found: " + role,
                "ROLE_NOT_FOUND",
                HttpStatus.NOT_FOUND
        );
    }
}

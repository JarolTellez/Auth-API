package com.jarol.auth.auth_api.dto.response;

import lombok.Builder;
import lombok.Data;


public record RevokeAllSessionsResponse (
        int revokedSessions
){


}

package com.jarol.auth.auth_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevokeAllSessionsResponse {

    int revokedSessions;
}

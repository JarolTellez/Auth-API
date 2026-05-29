package com.jarol.auth.auth_api.dto.response;

import java.util.List;

public record SessionsResponse(
        List<SessionResponse> sessions,
        int sessionCount
) {



}

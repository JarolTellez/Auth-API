package com.jarol.auth.auth_api.controller;

import com.jarol.auth.auth_api.config.CustomUserDetails;
import com.jarol.auth.auth_api.dto.response.RevokeAllSessionsResponse;
import com.jarol.auth.auth_api.dto.response.SessionResponse;
import com.jarol.auth.auth_api.dto.response.SessionsResponse;
import com.jarol.auth.auth_api.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    @PostMapping("/revoke-all")
    public ResponseEntity<RevokeAllSessionsResponse> revokeAllSessions(@AuthenticationPrincipal CustomUserDetails user){
        RevokeAllSessionsResponse response = sessionService.revokeAllSessions(user.getUserId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{sessionId}/revoke")
    public ResponseEntity<Void> revokeSession(@PathVariable UUID sessionId, @AuthenticationPrincipal CustomUserDetails user){

        sessionService.revokeSession(sessionId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sessions")
    public ResponseEntity<SessionsResponse> listActiveSessions(@AuthenticationPrincipal CustomUserDetails user){

        SessionsResponse sessions = sessionService.getActiveSessions(user.getUserId(), user.getSessionId());
        if(sessions.sessions().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sessions);
    }


}

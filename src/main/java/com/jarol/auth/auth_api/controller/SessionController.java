package com.jarol.auth.auth_api.controller;

import com.jarol.auth.auth_api.config.CustomUserDetails;
import com.jarol.auth.auth_api.dto.response.RevokeAllSessionsResponse;
import com.jarol.auth.auth_api.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    public ResponseEntity<>
}

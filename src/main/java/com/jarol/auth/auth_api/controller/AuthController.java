package com.jarol.auth.auth_api.controller;

import com.jarol.auth.auth_api.config.CustomUserDetails;
import com.jarol.auth.auth_api.dto.request.LoginRequest;
import com.jarol.auth.auth_api.dto.request.LogoutRequest;
import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.response.AuthResponse;
import com.jarol.auth.auth_api.dto.response.RevokeAllSessionsResponse;
import com.jarol.auth.auth_api.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final IAuthService authService;

    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request, HttpServletRequest httpRequest){
        AuthResponse response = authService.register(request, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest){
        AuthResponse response = authService.login(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal CustomUserDetails user){

        authService.logout(user.getSessionId());
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("logoutAll")
    public ResponseEntity<?> logoutAllSessions(@AuthenticationPrincipal CustomUserDetails user){
        RevokeAllSessionsResponse response = authService.logoutAllSessions(user.getUserId());
        return ResponseEntity.ok(response);
    }
}

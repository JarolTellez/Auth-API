package com.jarol.auth.auth_api.controller;

import com.jarol.auth.auth_api.config.CustomUserDetails;
import com.jarol.auth.auth_api.dto.request.LoginRequest;
import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.request.ResendVerificationRequest;
import com.jarol.auth.auth_api.dto.request.TokenRefreshRequest;
import com.jarol.auth.auth_api.dto.response.AuthResponse;
import com.jarol.auth.auth_api.dto.response.MessageResponse;
import com.jarol.auth.auth_api.dto.response.TokenRefreshResponse;
import com.jarol.auth.auth_api.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
        authService.register(request, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new MessageResponse("Registration successful. Please verify your email")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        AuthResponse response = authService.login(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@AuthenticationPrincipal CustomUserDetails user) {

        authService.logout(user.getSessionId(), user.getUserId());
        return ResponseEntity.ok(new MessageResponse("Successfully logged out"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {

        TokenRefreshResponse response = authService.refreshToken(request.refreshToken());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/verify")
    public ResponseEntity<MessageResponse> verifyAccount(@RequestParam("token") String token) {
        authService.verifyEmail(token);

        return ResponseEntity.ok(new MessageResponse("Email verified successfully."));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<MessageResponse> resendVerificationEmail(
            @Valid
            @RequestBody ResendVerificationRequest request) {

       authService.resendVerificationEmail(request.email());

        return ResponseEntity.ok(new MessageResponse("If the account exists and is not verified, a verification email has been sent."));
    }


}

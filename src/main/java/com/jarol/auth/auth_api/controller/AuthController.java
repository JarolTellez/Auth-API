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


/**
 * REST controller that exposes authentication-related endpoints,
 * including registration, login, logout, token refresh,
 * email verification, and verification email resending.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    /**
     * Registers a new user account and sends verification token to user's email to verify email user's account.
     *
     * @param request     registration data
     * @param httpRequest current HTTP request containing client metadata
     * @return a success message indication that email verification is required
     */
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
        authService.register(request, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new MessageResponse("Registration successful. Please verify your email")
        );
    }

    /**
     * Authenticates a user using identifier(username or email) and password.
     *
     * @param request     login credentials
     * @param httpRequest current HTTP request containing client metadata
     * @return authentication response containing access and refresh token and user details
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        AuthResponse response = authService.login(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Revokes the current user session.
     *
     * @param user authenticated user details
     * @return a success message indicating that the user logged out
     */
    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@AuthenticationPrincipal CustomUserDetails user) {

        authService.logout(user.getSessionId(), user.getUserId());
        return ResponseEntity.ok(new MessageResponse("Successfully logged out"));
    }

    /**
     * Generates a new access token using a valid refresh token
     *
     * @param request refresh token request
     * @return newly generated authentication token
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {

        TokenRefreshResponse response = authService.refreshToken(request.refreshToken());

        return ResponseEntity.ok(response);
    }

    /**
     * Verifies a user's email address using the provided verification token.
     *
     * @param token email verification token
     * @return a success message indicating that email has been verified
     */
    @GetMapping("/verify")
    public ResponseEntity<MessageResponse> verifyAccount(@RequestParam("token") String token) {
        authService.verifyEmail(token);

        return ResponseEntity.ok(new MessageResponse("Email verified successfully."));
    }

    /**
     * Resends the verification email if the account exists and has not been verified
     *
     * @param request request containing the user's email address
     * @return a success message indicating that a verification email may have been sent
     */
    @PostMapping("/resend-verification")
    public ResponseEntity<MessageResponse> resendVerificationEmail(
            @Valid
            @RequestBody ResendVerificationRequest request) {

        authService.resendVerificationEmail(request.email());

        return ResponseEntity.ok(new MessageResponse("If the account exists and is not verified, a verification email has been sent."));
    }


}

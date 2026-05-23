package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.request.LoginRequest;
import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {

    AuthResponse register(RegisterRequest request, HttpServletRequest httpRequest);
    AuthResponse login(LoginRequest request,HttpServletRequest httpRequest);
    void logout(String refreshToken);

}

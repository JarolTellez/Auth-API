package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.response.AuthResponse;

public interface IAuthService {

    AuthResponse register(RegisterRequest request);
}

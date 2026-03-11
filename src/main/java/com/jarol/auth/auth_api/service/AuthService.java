package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.response.AuthResponse;
import com.jarol.auth.auth_api.dto.response.UserResponse;
import com.jarol.auth.auth_api.mapper.IAuthMapper;
import com.jarol.auth.auth_api.mapper.IUserMapper;
import com.jarol.auth.auth_api.model.Role;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.repository.IRoleRepository;
import com.jarol.auth.auth_api.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final IUserMapper userMapper;
    private final IAuthMapper authMapper;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = userMapper.registerRequestToUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role roleUser = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Set.of(roleUser));

        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        return authMapper.userToAuthResponse(savedUser, accessToken, refreshToken,
                LocalDateTime.now().plus(Duration.ofMillis(jwtService.getRefreshExpiration())));


    }
}

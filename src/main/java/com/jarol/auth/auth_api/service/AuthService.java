package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.config.JwtProperties;
import com.jarol.auth.auth_api.dto.request.LoginRequest;
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
    private final JwtProperties jwtProperties;

    private final IUserMapper userMapper;
    private final IAuthMapper authMapper;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = userMapper.registerRequestToUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //MOVER EL "USER" A UNA VARIABLE DE ENTORNO
        Role roleUser = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Set.of(roleUser));

        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        // Calcular la fecha antes de llamar al mapper osea guardarlo en una variable y ya pasarselo al mapper
        return authMapper.userToAuthResponse(savedUser, accessToken, refreshToken,
                LocalDateTime.now().plus(Duration.ofMillis(jwtProperties.getRefreshExpiration())));


    }

    @Override
    public AuthResponse login(LoginRequest request) {

    }


}

package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.exception.TokenExpiredException;
import com.jarol.auth.auth_api.exception.TokenNotFoundException;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.VerificationToken;
import com.jarol.auth.auth_api.repository.IVerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationTokenService implements IVerificationTokenService{

    private final IVerificationTokenRepository verificationTokenRepository;
    private final TokenHashService tokenHashService;

    @Override
    public String createOrUpdateToken(User user) {
        verificationTokenRepository.findByUser(user)
                .ifPresent(verificationTokenRepository::delete);

        String token = UUID.randomUUID().toString();
        String tokenHash = tokenHashService.hashToken(token);

        VerificationToken verificationToken = VerificationToken.builder()
                .tokenHash(tokenHash)
                .user(user)
                .expiresAt(Instant.now().plus(Duration.ofMinutes(15)))
                .build();

        verificationTokenRepository.save(verificationToken);

        return token;
    }

    @Override
    public VerificationToken validateAndGetToken(String token) {
        String tokenHash= tokenHashService.hashToken(token);
        VerificationToken verificationToken=verificationTokenRepository.findByTokenHash(tokenHash).orElseThrow(()->
                new TokenNotFoundException("Verification link is invalid or has already been used"));

        if(verificationToken.isExpired()){
            verificationTokenRepository.delete(verificationToken);
            throw new TokenExpiredException("Verification token has expired. Request another one");
        }

        return verificationToken;

    }

    @Override
    public void deleteToken(VerificationToken token) {
        verificationTokenRepository.delete(token);
    }


}

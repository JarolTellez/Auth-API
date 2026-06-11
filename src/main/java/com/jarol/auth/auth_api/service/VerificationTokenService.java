package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.exception.TokenExpiredException;
import com.jarol.auth.auth_api.exception.TokenNotFoundException;
import com.jarol.auth.auth_api.exception.TooManyRequestsException;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.VerificationToken;
import com.jarol.auth.auth_api.repository.IVerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements IVerificationTokenService {

    private final IVerificationTokenRepository verificationTokenRepository;
    private final TokenHashService tokenHashService;

    @Value("${app.verification.token-expiration-seconds}")
    private long tokenExpirationSeconds;

    @Value("${app.verification.max-resends}")
    private int maxResends;

    @Value("${app.verification.resend-delay-seconds}")
    private long resendDelaySeconds;

    @Value("${app.verification.resend-limit-window-seconds}")
    private long resendWindowSeconds;

    @Override
    public String createOrUpdateToken(User user) {
        Instant now = Instant.now();

        String token = UUID.randomUUID().toString();
        String tokenHash = tokenHashService.hashToken(token);

        VerificationToken verificationToken = verificationTokenRepository
                .findByUser(user)
                .map(existingToken -> {

                    validateResendLimits(existingToken, now);

                    existingToken.setTokenHash(tokenHash);
                    existingToken.setExpiresAt(
                            now.plusSeconds(tokenExpirationSeconds)
                    );
                    existingToken.setLastEmailSentAt(now);
                    existingToken.setResendCount(
                            existingToken.getResendCount() + 1
                    );

                    return existingToken;
                })
                .orElseGet(() -> VerificationToken.builder()
                        .tokenHash(tokenHash)
                        .user(user)
                        .expiresAt(
                                now.plusSeconds(tokenExpirationSeconds)
                        )
                        .firstResendAt(now)
                        .lastEmailSentAt(now)
                        .resendCount(1)
                        .build());

        verificationTokenRepository.save(verificationToken);

        return token;
    }

    @Override
    public VerificationToken validateAndGetToken(String token) {
        String tokenHash = tokenHashService.hashToken(token);
        VerificationToken verificationToken = verificationTokenRepository.findByTokenHash(tokenHash).orElseThrow(() ->
                new TokenNotFoundException("Verification link is invalid or has already been used"));

        if (verificationToken.isExpired()) {
            verificationTokenRepository.delete(verificationToken);
            throw new TokenExpiredException("Verification token has expired. Request another one");
        }

        return verificationToken;

    }

    @Override
    public void deleteToken(VerificationToken token) {
        verificationTokenRepository.delete(token);
    }


    private void validateResendLimits(
            VerificationToken token,
            Instant now
    ) {
        resetResendWindowIfNeeded(token, now);

        if (token.getResendCount() >= maxResends) {
            throw new TooManyRequestsException(
                    "Maximum verification email requests reached. Try again later."
            );
        }

        if (token.getLastEmailSentAt() != null &&
                token.getLastEmailSentAt()
                        .isAfter(now.minusSeconds(resendDelaySeconds))) {

            throw new TooManyRequestsException(
                    "Please wait before requesting another verification email"
            );
        }
    }

    private void resetResendWindowIfNeeded(
            VerificationToken token,
            Instant now
    ) {
        if (token.getFirstResendAt() == null) {
            token.setFirstResendAt(now);
            return;
        }

        Instant resendWindowExpiration =
                token.getFirstResendAt().plusSeconds(resendWindowSeconds);

        if (now.isAfter(resendWindowExpiration)) {
            token.setResendCount(0);
            token.setFirstResendAt(now);
        }
    }
}

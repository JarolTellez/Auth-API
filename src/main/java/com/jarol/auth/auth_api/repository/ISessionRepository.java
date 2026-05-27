package com.jarol.auth.auth_api.repository;

import com.jarol.auth.auth_api.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface ISessionRepository extends JpaRepository<Session, UUID> {


    Optional<Session> findByRefreshTokenHash(String token);

    @Modifying
    @Transactional
    @Query(
            """
                    Update Session s
                    SET s.revoked = true,
                    s.revokedAt= :revokedAt
                    WHERE s.user.id= :userId
                    AND s.revoked =false
                    """
    )
    int revokeAllSessionByUserId(UUID userId, Instant revokedAt);

}

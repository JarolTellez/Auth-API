package com.jarol.auth.auth_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String refreshTokenHash;

    @Column(length = 100)
    private String deviceName;

    @Column(length = 45)
    private String ipAddress;

    @Column(length = 1024)
    private String userAgent;

    @CreationTimestamp
    private Instant createdAt;

    @Column
    private Instant lastUsedAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private Boolean revoked = false;

    @Column
    private Instant revokedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

package com.jarol.auth.auth_api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {

    private  String secret;
    private Long accessExpiration;
    private Long refreshExpiration;
    private String accessType;
    private String refreshType;
}

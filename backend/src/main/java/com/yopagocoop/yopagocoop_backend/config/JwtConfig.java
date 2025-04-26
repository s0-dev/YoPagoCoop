package com.yopagocoop.yopagocoop_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Component
@ConfigurationProperties(prefix = "jwt")
@Validated
public class JwtConfig {
    @NotBlank
    private String secret;
    @Positive
    private long expirationMs;
    @NotBlank
    private String issuer;

    public JwtConfig(String secret, long expirationMs, String issuer) {
        this.secret = secret;
        this.expirationMs = expirationMs;
        this.issuer = issuer;
    }

    public JwtConfig() {
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public void setExpirationMs(long expirationMs) {
        this.expirationMs = expirationMs;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
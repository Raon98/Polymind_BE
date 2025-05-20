package com.polymind.support.config.oauth.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.client.registration.google")
public record GoogleAuthRegistration(
        String clientId,
        String redirectUri,
        String clientSecret
){}


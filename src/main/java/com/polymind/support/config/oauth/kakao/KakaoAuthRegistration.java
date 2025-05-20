package com.polymind.support.config.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.client.registration.kakao")
public record KakaoAuthRegistration(
        String clientId,
        String redirectUri
){}


package com.polymind.support.config.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.client.registration.kakao")
public record KakaoAuthProperties(
        String clientId,
        String redirectUri
){}


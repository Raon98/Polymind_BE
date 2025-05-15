package com.polymind.support.config.login;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao.auth")
public record KakaoAuthProperties(
        String tokenUrl,
        String clientId,
        String redirectUri
){}
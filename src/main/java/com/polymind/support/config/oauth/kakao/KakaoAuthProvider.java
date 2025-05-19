package com.polymind.support.config.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.client.provider.kakao")
public record KakaoAuthProvider(
    String userInfoUrl,
    String authorizationUrl,
    String tokenUrl
){}

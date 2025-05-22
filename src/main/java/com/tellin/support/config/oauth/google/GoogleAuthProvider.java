package com.tellin.support.config.oauth.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.client.provider.google")
public record GoogleAuthProvider(
    String userInfoUrl,
    String authorizationUrl,
    String tokenUrl
){}

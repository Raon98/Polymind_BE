package com.polymind.support.config.cors;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cors")
public record CorsProperties(
        String allowedOrigins,
        String allowedHeaders,
        String allowedMethods,
        String allowCredentials
){}
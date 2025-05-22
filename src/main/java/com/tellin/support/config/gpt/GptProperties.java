package com.tellin.support.config.gpt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gpt")
public record GptProperties(
        String secretKey
){}
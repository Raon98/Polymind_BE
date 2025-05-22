package com.tellin.support.config.oauth.google;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({GoogleAuthProvider.class, GoogleAuthRegistration.class})
public class GoogleConfig {
}

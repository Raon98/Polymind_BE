package com.tellin.support.config.oauth.kakao;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({KakaoAuthRegistration.class, KakaoAuthProvider.class})
public class KaKaoConfig {
}

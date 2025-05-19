package com.polymind.support.config;
import com.polymind.support.config.cors.CorsProperties;
import com.polymind.support.config.oauth.kakao.KakaoAuthProperties;
import com.polymind.support.config.oauth.kakao.KakaoAuthProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({KakaoAuthProperties.class, KakaoAuthProvider.class, CorsProperties.class})
public class AppConfig {
}

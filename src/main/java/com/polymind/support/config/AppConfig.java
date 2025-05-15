package com.polymind.support.config;
import com.polymind.support.config.login.KakaoAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KakaoAuthProperties.class)
public class AppConfig {
}

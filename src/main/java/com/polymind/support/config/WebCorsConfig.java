package com.polymind.support.config;

import com.polymind.support.config.cors.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebCorsConfig implements WebMvcConfigurer {
    private final CorsProperties corsProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins(corsProperties.allowedOrigins().split(","))
                .allowedMethods(corsProperties.allowedMethods().split(","))
                .allowedHeaders(corsProperties.allowedHeaders().split(","))
                .allowCredentials(Boolean.TRUE.equals(corsProperties.allowCredentials()));
    }
}

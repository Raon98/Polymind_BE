package com.polymind.support.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class TemplateConfig {
    @Bean
    public TemplateConfig restTemplate() {
        return new TemplateConfig();
    }
}

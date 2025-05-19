package com.polymind.service.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginServiceFactory {

    private final Map<String, OAuth2LoginService> services;

    public OAuth2LoginService getService(String provider) {
        return services.get(provider.toLowerCase());
    }
}

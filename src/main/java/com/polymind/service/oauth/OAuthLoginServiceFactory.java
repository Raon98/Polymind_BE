package com.polymind.service.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthLoginServiceFactory {

    private final Map<String,OAuthLoginService> services;

    public OAuthLoginService getService(String provider) {
        return services.get(provider.toLowerCase());
    }
}

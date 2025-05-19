package com.polymind.support.security.oauth.validator;


import org.springframework.security.core.Authentication;

public interface OAuth2TokenValidator {
    boolean supports(String token);
    boolean validate(String token);
    Authentication getAuthentication(String token);
}

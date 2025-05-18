package com.polymind.support.security.oauth.validator;


import org.springframework.security.core.Authentication;

public interface OAuthTokenValidator {
    boolean supports(String token);
    boolean validate(String token);
    Authentication getAuthentication(String token);
}

package com.tellin.support.exception;

import com.tellin.dto.response.oauth.OAuth2ErrorResponse;
import lombok.Getter;

@Getter
public class OAuth2LoginException extends RuntimeException{
    private final OAuth2ErrorResponse error;

    public OAuth2LoginException(OAuth2ErrorResponse error) {
        super(error.getError_description());
        this.error = error;
    }
}

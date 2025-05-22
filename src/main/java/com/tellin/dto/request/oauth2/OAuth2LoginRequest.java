package com.tellin.dto.request.oauth2;

import lombok.Data;

@Data
public class OAuth2LoginRequest {
    private String provider;
    private String code;
}

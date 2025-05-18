package com.polymind.dto.request.login;

import lombok.Data;

@Data
public class OAuthLoginRequest {
    private String provider;
    private String code;
}

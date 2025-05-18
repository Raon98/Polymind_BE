package com.polymind.dto.response.oauth;

import lombok.Data;

@Data
public class TokenResponse {
    private String token_type;
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private int refresh_token_expires_in;
}

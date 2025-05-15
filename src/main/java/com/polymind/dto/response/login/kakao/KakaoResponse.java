package com.polymind.dto.response.login.kakao;

import lombok.Data;

@Data
public class KakaoResponse {
    private String token_type;
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private int refresh_token_expires_in;
    private String scope;
    private KakaoUser user;
}


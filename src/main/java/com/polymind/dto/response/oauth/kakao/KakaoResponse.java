package com.polymind.dto.response.oauth.kakao;

import com.polymind.dto.response.oauth.TokenResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class KakaoResponse extends TokenResponse {
    private String scope;
    private KakaoUser user;
}


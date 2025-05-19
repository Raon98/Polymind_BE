package com.polymind.dto.response.oauth.kakao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class KakaoResultOAuth2Access  {
    private String scope;
    private KakaoUser user;

}


package com.polymind.service.oauth2;

import com.polymind.dto.request.oauth2.OAuth2LoginRequest;
import com.polymind.dto.response.oauth.OAuth2AccessTokenResponse;
import com.polymind.dto.response.oauth.OAuth2LoginResult;
import com.polymind.dto.response.oauth.kakao.KakaoUser;
import com.polymind.entity.user.User;

public interface OAuth2LoginService {
    OAuth2LoginResult LoginProcess(OAuth2LoginRequest request);

    OAuth2AccessTokenResponse getToken(String code);

    User getUserInfo(String accessToken);

}

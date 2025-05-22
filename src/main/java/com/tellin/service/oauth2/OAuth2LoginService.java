package com.tellin.service.oauth2;

import com.tellin.dto.request.oauth2.OAuth2LoginRequest;
import com.tellin.dto.response.oauth.OAuth2AccessTokenResponse;
import com.tellin.dto.response.oauth.OAuth2LoginResult;
import com.tellin.entity.user.User;

public interface OAuth2LoginService {
    OAuth2LoginResult LoginProcess(OAuth2LoginRequest request);

    OAuth2AccessTokenResponse getToken(String code);

    User getUserInfo(String accessToken);

}

package com.polymind.service.oauth;

import com.polymind.dto.request.login.OAuthLoginRequest;
import com.polymind.dto.response.oauth.OAuthLoginResult;

public interface OAuthLoginService {
    OAuthLoginResult LoginProcess(OAuthLoginRequest request);
}

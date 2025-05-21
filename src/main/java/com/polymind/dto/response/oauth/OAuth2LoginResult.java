package com.polymind.dto.response.oauth;

import com.polymind.dto.response.jwt.JwtDto;
import com.polymind.entity.user.User;
import lombok.Data;

@Data
public class OAuth2LoginResult  {
    private boolean success;
    private OAuth2SuccessResponse data;
    private OAuth2ErrorResponse error;

    public static OAuth2LoginResult success(User user, JwtDto token){
        OAuth2LoginResult result = new OAuth2LoginResult();
        result.setSuccess(true);
        result.setData(OAuth2SuccessResponse.builder()
                .user(user)
                .tokenResponse(token)
                .build());
        return result;
    }

    public static OAuth2LoginResult error(OAuth2ErrorResponse error) {
        OAuth2LoginResult result = new OAuth2LoginResult();
        result.success = false;
        result.error = error;
        return result;
    }
}

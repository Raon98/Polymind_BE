package com.polymind.dto.response.oauth;

import com.polymind.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class OAuthLoginResult extends TokenResponse{
    private User user;
    private boolean success;
    private OAuthErrorResponse error;
}

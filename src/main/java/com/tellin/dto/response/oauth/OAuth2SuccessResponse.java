package com.tellin.dto.response.oauth;

import com.tellin.dto.response.jwt.JwtDto;
import com.tellin.entity.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class OAuth2SuccessResponse  {
    private User user;
    private JwtDto tokenResponse;
}

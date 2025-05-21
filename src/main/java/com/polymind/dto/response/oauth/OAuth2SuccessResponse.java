package com.polymind.dto.response.oauth;

import com.polymind.dto.response.jwt.JwtDto;
import com.polymind.entity.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class OAuth2SuccessResponse  {
    private User user;
    private JwtDto tokenResponse;
}

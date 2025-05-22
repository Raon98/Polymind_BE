package com.tellin.dto.request.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtUserDto {
    private String userId;
    private String email;
    private String role;
    private String provider;
}

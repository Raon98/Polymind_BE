package com.tellin.dto.response.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OAuth2ErrorResponse {
    /*error*/
    private String error;
    private String error_description;
    private int error_code;
}

package com.polymind.dto.response.oauth;

import lombok.Data;

@Data
public class OAuthErrorResponse {
    /*error*/
    private String error;
    private String error_description;
    private String error_code;
}

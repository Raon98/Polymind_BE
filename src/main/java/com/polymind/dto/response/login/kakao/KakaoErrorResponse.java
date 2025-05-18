package com.polymind.dto.response.login.kakao;

import lombok.Data;

@Data
public class KakaoErrorResponse {
    /*error*/
    private String error;
    private String error_description;
    private String error_code;
}

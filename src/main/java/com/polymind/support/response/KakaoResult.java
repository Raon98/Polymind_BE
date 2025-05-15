package com.polymind.support.response;

import com.polymind.dto.response.login.KakaoErrorResponse;
import com.polymind.dto.response.login.KakaoResponse;
import lombok.Data;

@Data
public class KakaoResult {
    private boolean success;
    private KakaoResponse data;
    private KakaoErrorResponse error;
}

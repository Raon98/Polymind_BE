package com.polymind.support.response;

import com.polymind.dto.response.login.kakao.KakaoErrorResponse;
import com.polymind.dto.response.login.kakao.KakaoResponse;
import lombok.Data;

@Data
public class KakaoResult {
    private boolean success;
    private KakaoResponse data;
    private KakaoErrorResponse error;
}

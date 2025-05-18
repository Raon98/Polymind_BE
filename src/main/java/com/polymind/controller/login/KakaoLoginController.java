package com.polymind.controller.login;

import com.polymind.dto.request.login.KakaoRequest;
import com.polymind.service.login.KakaoLoginService;
import com.polymind.support.response.ApiResponseEntity;
import com.polymind.support.response.KakaoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/api/kakao")
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity<?>> login(@RequestBody KakaoRequest request) throws IOException, InterruptedException {

        KakaoResult result = kakaoLoginService.kakaoLoginProcess(request);

        return ResponseEntity.ok(
                ApiResponseEntity.builder()
                        .data(result.isSuccess() ? result.getData() : result.getError())
                        .build()
        );
    }
}

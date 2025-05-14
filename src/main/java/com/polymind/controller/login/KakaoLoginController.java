package com.polymind.controller.login;

import com.polymind.dto.req.login.KakaoRequest;
import com.polymind.support.utils.ApiResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/kakao")
public class KakaoLoginController {

    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity<?>> login(@RequestBody KakaoRequest request){

        String code = request.getCode();
        return ResponseEntity.ok(
                ApiResponseEntity.builder()
                        .data(code)
                        .build()
        );
    }
}

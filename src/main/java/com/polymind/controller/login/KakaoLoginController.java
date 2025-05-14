package com.polymind.controller.login;
import com.polymind.support.utils.ApiResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/kakao")
public class KakaoLoginController {

    @RequestMapping("/login")
    public ApiResponseEntity<?> login(@RequestBody String code){

        return ApiResponseEntity.builder()
                .data("test")
                .build();
    }
}

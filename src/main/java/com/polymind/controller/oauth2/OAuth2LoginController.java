package com.polymind.controller.oauth2;

import com.polymind.dto.request.oauth2.OAuth2LoginRequest;
import com.polymind.dto.response.oauth.OAuth2LoginResult;
import com.polymind.service.oauth2.OAuth2LoginService;
import com.polymind.service.oauth2.OAuth2LoginServiceFactory;
import com.polymind.support.response.ApiResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/oauth")
@RequiredArgsConstructor
public class OAuth2LoginController {

    private final OAuth2LoginServiceFactory oAuth2LoginServiceFactory;
    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity<?>> OAuthLoginProcess(@RequestBody OAuth2LoginRequest request){

        OAuth2LoginService loginService = oAuth2LoginServiceFactory.getService(request.getProvider());
        OAuth2LoginResult result = loginService.LoginProcess(request);

        return ResponseEntity.ok(
                ApiResponseEntity.builder()
                        .data(result.isSuccess() ? result.getData(): result.getError())
                        .build()
        );
    }
}

package com.polymind.controller.oauth;

import com.polymind.dto.request.login.OAuthLoginRequest;
import com.polymind.dto.response.oauth.OAuthLoginResult;
import com.polymind.service.oauth.OAuthLoginService;
import com.polymind.service.oauth.OAuthLoginServiceFactory;
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
public class OAuthLoginController {

    private final OAuthLoginServiceFactory oAuthLoginServiceFactory;
    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity<?>> OAuthLoginProcess(@RequestBody OAuthLoginRequest request){

        OAuthLoginService loginService = oAuthLoginServiceFactory.getService(request.getProvider());
        OAuthLoginResult result = loginService.LoginProcess(request);

        return ResponseEntity.ok(
                ApiResponseEntity.builder()
                        .data(result.isSuccess() ? result.getUser(): result.getError())
                        .build()
        );
    }
}

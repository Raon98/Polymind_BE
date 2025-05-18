package com.polymind.support.security.oauth.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
public class KakaoTokenValidator implements OAuthTokenValidator{

    private final RestTemplate restTemplate;

    @Override
    public boolean supports(String token) {
        return !token.contains("Bearer ");
    }

    @Override
    public boolean validate(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, entity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        }catch(HttpClientErrorException e){
            return false;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken("kakaoUser", null, List.of());
    }
}

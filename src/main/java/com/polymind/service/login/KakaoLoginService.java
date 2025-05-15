package com.polymind.service.login;

import com.polymind.dto.request.login.KakaoRequest;
import com.polymind.dto.response.login.KakaoErrorResponse;
import com.polymind.dto.response.login.KakaoResponse;
import com.polymind.support.config.login.KakaoAuthProperties;
import com.polymind.support.response.KakaoResult;
import com.polymind.support.utils.HttpClientUtils;
import com.polymind.support.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class KakaoLoginService {
    private final KakaoAuthProperties kakaoAuthProperties;

    public KakaoResult getKakaoToken(KakaoRequest kakaoRequest) throws IOException, InterruptedException {

        String url = kakaoAuthProperties.tokenUrl();
        String clientId = kakaoAuthProperties.clientId();
        String redirectUri = kakaoAuthProperties.redirectUri();
        String authorizeCode = kakaoRequest.getCode();

        Map<String,String> params = Map.of(
                "grant_type", "authorization_code",
                "client_id", clientId,
                "redirect_uri", redirectUri,
                "code", authorizeCode
        );
        KakaoResult kakaoResult = new KakaoResult();

        HttpResponse<String> httpResponse = HttpClientUtils.sendPostForm(url,params);
        if(httpResponse.statusCode() == 200){
            KakaoResponse response = ObjectMapperUtils.readValue(httpResponse.body(),KakaoResponse.class);
            kakaoResult.setSuccess(true);
            kakaoResult.setData(response);
        }else{
            KakaoErrorResponse error = ObjectMapperUtils.readValue(httpResponse.body(),KakaoErrorResponse.class);
            kakaoResult.setSuccess(false);
            kakaoResult.setError(error);
        }

        return kakaoResult;
    }
}

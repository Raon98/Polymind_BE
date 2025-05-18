package com.polymind.service.oauth;

import com.polymind.dto.request.login.OAuthLoginRequest;
import com.polymind.dto.response.oauth.OAuthErrorResponse;
import com.polymind.dto.response.oauth.OAuthLoginResult;
import com.polymind.dto.response.oauth.kakao.KakaoResponse;
import com.polymind.dto.response.oauth.kakao.KakaoUser;
import com.polymind.entity.user.User;
import com.polymind.service.user.UserService;
import com.polymind.support.config.login.KakaoAuthProperties;
import com.polymind.support.logging.Log;
import com.polymind.support.utils.HttpClientUtils;
import com.polymind.support.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

@RequiredArgsConstructor
@Service(value = "kakao")
public class KakaoLoginService implements OAuthLoginService {
    private final KakaoAuthProperties kakaoAuthProperties;
    private final UserService userService;
    /**
     * 카카오 토큰 발급
     * @param kakaoRequest
     * @return
     * @throws IOException
     * @throws InterruptedException
     */

    public OAuthLoginResult LoginProcess(OAuthLoginRequest kakaoRequest) throws IOException, InterruptedException {
        Log.info("[START] ::::::: [LoginProcess : kakao]");
        String tokenUrl = kakaoAuthProperties.tokenUrl();
        String clientId = kakaoAuthProperties.clientId();
        String redirectUri = kakaoAuthProperties.redirectUri();
        String authorizeCode = kakaoRequest.getCode();
        String userUrl = kakaoAuthProperties.userUrl();
        Map<String,String> params = Map.of(
                "grant_type", "authorization_code",
                "client_id", clientId,
                "redirect_uri", redirectUri,
                "code", authorizeCode
        );

        OAuthLoginResult oAuthLoginResult = new OAuthLoginResult();

        HttpResponse<String> httpTokenResponse = HttpClientUtils.sendPostForm(tokenUrl,params);
        Log.info("[LoginProcess : kakao : httpTokenResponse : {}]",httpTokenResponse);
        if(httpTokenResponse.statusCode() == 200){
            KakaoResponse tokenResponse = ObjectMapperUtils.readValue(httpTokenResponse.body(),KakaoResponse.class);

            HttpResponse<String> httpUserResponse = HttpClientUtils.sendAuthorization(userUrl,tokenResponse.getAccess_token());
            Log.info("[LoginProcess : kakao : httpUserResponse : {}]",httpUserResponse);
            if(httpUserResponse.statusCode() == 200){
                KakaoUser userResponse = ObjectMapperUtils.readValue(httpUserResponse.body(),KakaoUser.class);

                User user = userService.saveIfNotExists(userResponse.getId(),"",userResponse.getProperties().getName(),"kakao",userResponse.getProperties().getProfileImage());
                if(user != null){
                    oAuthLoginResult.setSuccess(true);
                    oAuthLoginResult.setUser(user);
                    oAuthLoginResult.setAccess_token(tokenResponse.getAccess_token());
                    oAuthLoginResult.setRefresh_token(tokenResponse.getRefresh_token());
                    oAuthLoginResult.setExpires_in(tokenResponse.getExpires_in());
                    oAuthLoginResult.setRefresh_token_expires_in(tokenResponse.getRefresh_token_expires_in());
                    oAuthLoginResult.setToken_type(tokenResponse.getToken_type());

                    Log.info("[LoginProcess : kakao : 카카오 로그인 성공 : {}]",oAuthLoginResult);
                }
            }else {
                throw new RuntimeException("카카오 사용자 정보 조회 실패: " + httpUserResponse.body());
            }
        }else{
            OAuthErrorResponse error = ObjectMapperUtils.readValue(httpTokenResponse.body(),OAuthErrorResponse.class);
            oAuthLoginResult.setSuccess(false);
            oAuthLoginResult.setError(error);
            throw new RuntimeException("카카오 토큰 발급 실패: " + httpTokenResponse.body());
        }

        return oAuthLoginResult;
    }

}

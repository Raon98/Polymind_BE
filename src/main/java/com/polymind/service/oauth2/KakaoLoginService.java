package com.polymind.service.oauth2;

import com.polymind.dto.request.oauth2.OAuth2LoginRequest;
import com.polymind.dto.response.oauth.OAuth2AccessTokenResponse;
import com.polymind.dto.response.oauth.OAuth2ErrorResponse;
import com.polymind.dto.response.oauth.OAuth2LoginResult;
import com.polymind.dto.response.oauth.kakao.KakaoUser;
import com.polymind.entity.user.User;
import com.polymind.service.user.UserService;
import com.polymind.support.config.oauth.kakao.KakaoAuthProperties;
import com.polymind.support.config.oauth.kakao.KakaoAuthProvider;
import com.polymind.support.exception.OAuth2LoginException;
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
public class KakaoLoginService implements OAuth2LoginService {
    private final KakaoAuthProperties kakaoAuthProperties;
    private final KakaoAuthProvider kakaoAuthProvider;
    private final UserService userService;

    /**
     * 카카오 토큰 발급
     * @param kakaoRequest
     * @return OAuthLoginResult
     */

    @Override
    public OAuth2LoginResult LoginProcess(OAuth2LoginRequest kakaoRequest) {
        Log.info("[START] ::::::: [LoginProcess : kakao]");

        //1. token 조회
        OAuth2AccessTokenResponse token = getToken(kakaoRequest.getCode());
        //2. User정보 조회
        User user = getUserInfo(token.getAccess_token());
        //3. user정보 저장
        userService.saveIfNotExists(user.getUserId(), "", user.getName(), "kakao", user.getProfile_image());
        //4. 사용자 리턴
        return OAuth2LoginResult.success(user,token);
    }

    @Override
    public OAuth2AccessTokenResponse getToken(String code) {
        try {
            String tokenUrl = kakaoAuthProvider.tokenUrl();
            String clientId = kakaoAuthProperties.clientId();
            String redirectUri = kakaoAuthProperties.redirectUri();
            String authorizeCode = code;

            Map<String,String> params = Map.of(
                    "grant_type", "authorization_code",
                    "client_id", clientId,
                    "redirect_uri", redirectUri,
                    "code", authorizeCode
            );

            HttpResponse<String> response = HttpClientUtils.sendPostForm(tokenUrl, params);
            if (response.statusCode() != 200) {
                Log.info("[LoginProcess : kakao : 카카오 로그인 TOKEN 발급실패 : {}]",response);
                throw new OAuth2LoginException(
                        OAuth2ErrorResponse.builder()
                                .error("kakao_token_error_001" )
                                .error_description("카카오 토큰 발급 실패")
                                .error_code(response.statusCode())
                                .build()
                );
            }
            return ObjectMapperUtils.readValue(response.body(), OAuth2AccessTokenResponse.class);
        } catch (IOException | InterruptedException e) {
            Log.info("[LoginProcess : kakao : 카카오 로그인 TOKEN 발급실패 : {}]",e);
            throw new OAuth2LoginException(
                    OAuth2ErrorResponse.builder()
                            .error("kakao_token_error : "+e)
                            .error_description("카카오 토큰 발급중 IO 통신오류 실패")
                            .error_code(500)
                            .build()
            );
        }

    }

    @Override
    public User getUserInfo(String accessToken) {
        try {
            String userUrl = kakaoAuthProvider.userInfoUrl();
            HttpResponse<String> httpUserResponse = HttpClientUtils.sendAuthorization(userUrl,accessToken);

            if (httpUserResponse.statusCode() != 200) {
                Log.error("[LoginProcess : kakao : 카카오 유저정보 조회실패 : {}]",httpUserResponse);
                throw new OAuth2LoginException(
                        OAuth2ErrorResponse.builder()
                                .error("kakao_userInfo_error_001")
                                .error_description("카카오 유저정보 조회실패 ")
                                .error_code(httpUserResponse.statusCode())
                                .build()
                );
            }
            Log.info("kakao response {} ",httpUserResponse.body() );

            KakaoUser kakaoUser = ObjectMapperUtils.readValue(httpUserResponse.body(),KakaoUser.class);

            return User.builder()
                    .userId(kakaoUser.getId())
                    .email("")
                    .name(kakaoUser.getProperties().getName())
                    .profile_image(kakaoUser.getProperties().getProfileImage())
                    .build();
        } catch (IOException | InterruptedException e) {
            throw new OAuth2LoginException(
                    OAuth2ErrorResponse.builder()
                            .error("kakao_userInfo_error_002 : "+e)
                            .error_description("카카오 유저정보 조회중 IO 통신오류 실패")
                            .error_code(500)
                            .build()
            );
        }
    }

}
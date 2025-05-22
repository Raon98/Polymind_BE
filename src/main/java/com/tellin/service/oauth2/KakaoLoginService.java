package com.tellin.service.oauth2;

import com.tellin.dto.request.jwt.JwtUserDto;
import com.tellin.dto.request.oauth2.OAuth2LoginRequest;
import com.tellin.dto.response.jwt.JwtDto;
import com.tellin.dto.response.oauth.OAuth2AccessTokenResponse;
import com.tellin.dto.response.oauth.OAuth2ErrorResponse;
import com.tellin.dto.response.oauth.OAuth2LoginResult;
import com.tellin.dto.response.oauth.kakao.KakaoUser;
import com.tellin.entity.user.User;
import com.tellin.service.user.UserService;
import com.tellin.support.config.oauth.kakao.KakaoAuthRegistration;
import com.tellin.support.config.oauth.kakao.KakaoAuthProvider;
import com.tellin.support.exception.OAuth2LoginException;
import com.tellin.support.logging.Log;
import com.tellin.support.utils.HttpClientUtils;
import com.tellin.support.utils.JwtUtils;
import com.tellin.support.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@Service(value = "kakao")
public class KakaoLoginService implements OAuth2LoginService {
    private final KakaoAuthRegistration kakaoAuthRegistration;
    private final KakaoAuthProvider kakaoAuthProvider;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    /**
     * 카카오 토큰 발급
     * @param OAuth2LoginRequest
     * @return OAuth2LoginResult
     */

    @Override
    public OAuth2LoginResult LoginProcess(OAuth2LoginRequest request) {
        Log.info("[START] ::::::: [LoginProcess : kakao]");

        //1. token 조회
        OAuth2AccessTokenResponse token = getToken(request.getCode());
        //2. User정보 조회
        User user = getUserInfo(token.getAccess_token());
        //3. user정보 저장
        userService.saveIfNotExists(user.getUserId(), "", user.getName(), "kakao", user.getProfile_image());

        //4. JwtToken 발급
        JwtUserDto jwtUser = JwtUserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .role("USER")
                .provider(user.getProvider())
                .build();
        JwtDto jwtToken = jwtUtils.generateToken(jwtUser);

        return OAuth2LoginResult.success(user,jwtToken);
    }

    @Override
    public OAuth2AccessTokenResponse getToken(String code) {
        try {
            String tokenUrl = kakaoAuthProvider.tokenUrl();
            String clientId = kakaoAuthRegistration.clientId();
            String redirectUri = kakaoAuthRegistration.redirectUri();
            String authorizeCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

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
                    .userId(kakaoUser.getId().toString())
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
package com.tellin.service.oauth2;

import com.tellin.dto.request.jwt.JwtUserDto;
import com.tellin.dto.request.oauth2.OAuth2LoginRequest;
import com.tellin.dto.response.jwt.JwtDto;
import com.tellin.dto.response.oauth.OAuth2AccessTokenResponse;
import com.tellin.support.exception.ErrorResponse;
import com.tellin.dto.response.oauth.OAuth2LoginResult;
import com.tellin.dto.response.oauth.google.GoogleUser;
import com.tellin.entity.user.User;
import com.tellin.service.user.UserService;
import com.tellin.support.config.oauth.google.GoogleAuthProvider;
import com.tellin.support.config.oauth.google.GoogleAuthRegistration;
import com.tellin.support.exception.ErrorException;
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
@Service(value = "google")
public class GoogleLoginService implements OAuth2LoginService {
    private final GoogleAuthRegistration googleAuthRegistration;
    private final GoogleAuthProvider googleAuthProvider;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    /**
     * 카카오 토큰 발급
     * @param OAuth2LoginRequest
     * @return OAuth2LoginResult
     */

    @Override
    public OAuth2LoginResult LoginProcess(OAuth2LoginRequest request) {
        Log.info("[START] ::::::: [LoginProcess : Google]");

        //1. token 조회
        OAuth2AccessTokenResponse token = getToken(request.getCode());
        //2. User정보 조회
        User user = getUserInfo(token.getAccess_token());
        //3. user정보 저장
        userService.saveIfNotExists(user.getUserId().toString(), user.getEmail(), user.getName(), "google", user.getProfile_image());
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
            String tokenUrl = googleAuthProvider.tokenUrl();
            String clientId = googleAuthRegistration.clientId();
            String clientSecret = googleAuthRegistration.clientSecret();
            String redirectUri = googleAuthRegistration.redirectUri();
            String authorizeCode = URLDecoder.decode(code, StandardCharsets.UTF_8);;

            Map<String,String> params = Map.of(
                    "grant_type", "authorization_code",
                    "client_id", clientId,
                    "client_secret",clientSecret,
                    "redirect_uri", redirectUri,
                    "code", authorizeCode
            );
            Log.info("[LoginProcess : Google : 구글 로그인 params 확인 : {}]",params);

            HttpResponse<String> response = HttpClientUtils.sendPostForm(tokenUrl, params);

            Log.info("[LoginProcess : Google : 구글 로그인 response 확인 : {}]",response.body());
            if (response.statusCode() != 200) {
                Log.info("[LoginProcess : Google : 구글 로그인 TOKEN 발급실패 : {}]",response);
                throw new ErrorException(
                        ErrorResponse.builder()
                                .error("google_token_error_001" )
                                .error_description("구글 토큰 발급 실패")
                                .error_code(response.statusCode())
                                .build()
                );
            }
            return ObjectMapperUtils.readValue(response.body(), OAuth2AccessTokenResponse.class);
        } catch (IOException | InterruptedException e) {
            Log.info("[LoginProcess : kakao : 카카오 로그인 TOKEN 발급실패 : {}]",e);
            throw new ErrorException(
                    ErrorResponse.builder()
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
            String userUrl = googleAuthProvider.userInfoUrl();
            HttpResponse<String> httpUserResponse = HttpClientUtils.sendAuthorization(userUrl,accessToken);

            if (httpUserResponse.statusCode() != 200) {
                Log.error("[LoginProcess : Google : 구글 유저정보 조회실패 : {}]",httpUserResponse);
                throw new ErrorException(
                        ErrorResponse.builder()
                                .error("google_userInfo_error_001")
                                .error_description("구글 유저정보 조회실패 ")
                                .error_code(httpUserResponse.statusCode())
                                .build()
                );
            }
            Log.info("google response {} ",httpUserResponse.body() );

            GoogleUser googleUser = ObjectMapperUtils.readValue(httpUserResponse.body(), GoogleUser.class);

            return User.builder()
                    .userId(googleUser.getId())
                    .email(googleUser.getEmail())
                    .name(googleUser.getName())
                    .profile_image(googleUser.getPicture())
                    .build();
        } catch (IOException | InterruptedException e) {
            throw new ErrorException(
                    ErrorResponse.builder()
                            .error("google_userInfo_error_002 : "+e)
                            .error_description("구글 유저정보 조회중 IO 통신오류 실패")
                            .error_code(500)
                            .build()
            );
        }
    }

}
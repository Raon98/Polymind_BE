package com.polymind.service.login;

import com.polymind.dto.request.login.KakaoRequest;
import com.polymind.dto.response.login.kakao.KakaoErrorResponse;
import com.polymind.dto.response.login.kakao.KakaoResponse;
import com.polymind.dto.response.login.kakao.KakaoUser;
import com.polymind.entity.user.User;
import com.polymind.repository.user.UserRepository;
import com.polymind.support.config.login.KakaoAuthProperties;
import com.polymind.support.logging.Log;
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
    private final UserRepository userRepository;
    /**
     * 카카오 토큰 발급
     * @param kakaoRequest
     * @return
     * @throws IOException
     * @throws InterruptedException
     */

    public KakaoResult getKakaoTokenLogin(KakaoRequest kakaoRequest) throws IOException, InterruptedException {
        Log.info("[START] ::::::: [getKakaoTokenLogin]");
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
        KakaoResult kakaoResult = new KakaoResult();

        HttpResponse<String> httpTokenResponse = HttpClientUtils.sendPostForm(tokenUrl,params);
        Log.info("[getKakaoTokenLogin : httpTokenResponse : {}]",httpTokenResponse);
        if(httpTokenResponse.statusCode() == 200){
            KakaoResponse tokenResponse = ObjectMapperUtils.readValue(httpTokenResponse.body(),KakaoResponse.class);

            HttpResponse<String> httpUserResponse = HttpClientUtils.sendAuthorization(userUrl,tokenResponse.getAccess_token());
            Log.info("[getKakaoTokenLogin : httpUserResponse : {}]",httpUserResponse);
            if(httpUserResponse.statusCode() == 200){
                KakaoUser userResponse = ObjectMapperUtils.readValue(httpUserResponse.body(),KakaoUser.class);
                // User에게 토큰 반환
                tokenResponse.setUser(userResponse);

                // DB 저장
                kakaoUserSave(userResponse);
            }else {
                throw new RuntimeException("카카오 사용자 정보 조회 실패: " + httpUserResponse.body());
            }
            kakaoResult.setSuccess(true);
            kakaoResult.setData(tokenResponse);
        }else{
            KakaoErrorResponse error = ObjectMapperUtils.readValue(httpTokenResponse.body(),KakaoErrorResponse.class);
            kakaoResult.setSuccess(false);
            kakaoResult.setError(error);
            throw new RuntimeException("카카오 토큰 발급 실패: " + httpTokenResponse.body());
        }

        return kakaoResult;
    }

    public User kakaoUserSave(KakaoUser kakaoUser){
        User user = User.builder()
                .userId(kakaoUser.getId())
                .email("")
                .name(kakaoUser.getProperties().getNickname())
                .plat("kakao")
                .profile_image(kakaoUser.getProperties().getProfileImage())
                .build();
        return userRepository.save(user);
    }
}

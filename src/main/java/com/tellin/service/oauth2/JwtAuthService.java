package com.tellin.service.oauth2;

import com.tellin.dto.request.jwt.JwtUserDto;
import com.tellin.dto.response.jwt.JwtDto;
import com.tellin.entity.user.User;
import com.tellin.repository.user.UserRepository;
import com.tellin.support.exception.ErrorException;
import com.tellin.support.exception.ErrorResponse;
import com.tellin.support.logging.Log;
import com.tellin.support.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    public JwtDto JwtRefreshAccessToken (String refreshToken) {
        if (jwtUtils.isTokenExpired(refreshToken) || !jwtUtils.validateToken(refreshToken)) {
            Log.error("[JwtRefreshAccessToken 발급실패 isTokenExpired >> {}]" , !jwtUtils.isTokenExpired(refreshToken));
            Log.error("[JwtRefreshAccessToken 발급실패 validateToken >> {}]" , !jwtUtils.validateToken(refreshToken));

            throw new ErrorException(
                    ErrorResponse.builder()
                            .error("token isExpired or validate error" )
                            .error_description("사용기간이 만료되어 로그인화면으로 이동합니다.")
                            .error_code(403)
                            .build());
        }

        String userid = jwtUtils.getUserIdFromToken(refreshToken);

        JwtUserDto user = loadUserFromDatabase(userid);
        return jwtUtils.generateToken(user);

    }

    private JwtUserDto loadUserFromDatabase(String userId) {
        // TODO: 실제 사용자 정보 조회
        User user = userRepository.findById(userId).orElseThrow(()->new ErrorException(
                ErrorResponse.builder()
                        .error("USER NOT FOUND" )
                        .error_description("사용자 정보를 확인할 수 없습니다.")
                        .error_code(500)
                        .build()
        ));
        return JwtUserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .provider(user.getProvider())
                .build();
    }
}

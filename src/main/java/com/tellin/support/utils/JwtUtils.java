package com.tellin.support.utils;

import com.tellin.dto.request.jwt.JwtUserDto;
import com.tellin.dto.response.jwt.JwtDto;
import com.tellin.support.config.jwt.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtProperties jwtProperties;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtDto generateToken(JwtUserDto user) {
        long now = (new Date()).getTime();

        Date accessTokenExpiration = new Date(now + 1000 * 60 * 30);
        String accessToken = Jwts.builder()
                .setSubject(user.getUserId())
                .setExpiration(accessTokenExpiration)
                .claim("email", user.getEmail())
                .claim("roles", user.getRole())
                .claim("provider",user.getProvider())
                .signWith(key)
                .compact();

        Date refreshTokenExpiration = new Date(now + 1000L * 60 * 60 * 24 * 7);
        String refreshToken = Jwts.builder()
                .setSubject(user.getUserId())
                .claim("roles", user.getRole())
                .claim("provider",user.getProvider())
                .setExpiration(refreshTokenExpiration)
                .signWith(key)
                .compact();

        return JwtDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
             return false;
        }
    }

    public String getUserIdFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}

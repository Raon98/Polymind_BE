package com.polymind.support.filter;

import com.polymind.support.security.oauth.validator.OAuth2TokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org. springframework. security. core. Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuth2TokenFilter extends GenericFilterBean {

    private final List<OAuth2TokenValidator> validators;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        if(authHeader !=null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);

            for(OAuth2TokenValidator validator : validators){
                if(validator.supports(token) && validator.validate(token)){
                    Authentication auth = validator.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    break;
                }
            }
        }
        chain.doFilter(request, response);
    }
}

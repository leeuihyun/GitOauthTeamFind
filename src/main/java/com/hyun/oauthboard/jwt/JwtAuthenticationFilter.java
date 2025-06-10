package com.hyun.oauthboard.jwt;

import com.hyun.oauthboard.security.SecurityConstants;
import com.hyun.oauthboard.service.JwtBlacklistService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtBlacklistService jwtBlacklistService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        if (SecurityConstants.EXCLUDE_URLS.stream()
            .anyMatch(requestURI::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        String bearerToken = httpServletRequest.getHeader("Authorization");
        String token = jwtTokenProvider.resolveToken(bearerToken);

        try {
            if (token == null) {
                throw new SecurityException("JWT token is missing");
            }

            if (!jwtTokenProvider.validateToken(token)) {
                throw new SecurityException("JWT token is not validated");
            }

            if (jwtBlacklistService.isBlackList(token)) {
                throw new SecurityException("JWT token is in blacklist");
            }

            Authentication auth = jwtTokenProvider.getAccessAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(request, response);
        } catch (Exception e) {
            ((HttpServletResponse) response).sendRedirect("/login");
        }
    }
}

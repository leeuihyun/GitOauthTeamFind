package com.hyun.oauthboard.controller;

import com.hyun.oauthboard.domain.dto.OAuthAccessTokenRequest;
import com.hyun.oauthboard.domain.dto.OAuthTokensResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Controller
public class AuthController {
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @GetMapping("/login/oauth2/code/github")
    public ResponseEntity<OAuthTokensResponse> getMemberGithubInfo(@RequestParam final String code) {
        final OAuthTokensResponse response = getTokensInfo(code);
        return ResponseEntity.ok().body(response);
    }

    public OAuthTokensResponse getTokensInfo(final String code) {
        return restTemplate.postForObject(
            ACCESS_TOKEN_URL,
            new OAuthAccessTokenRequest(clientId, clientSecret, code),
            OAuthTokensResponse.class
        );
    }
}

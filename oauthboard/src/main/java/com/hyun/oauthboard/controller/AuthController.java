package com.hyun.oauthboard.controller;

import com.hyun.oauthboard.domain.dto.github.GithubMemberInfo;
import com.hyun.oauthboard.domain.dto.github.OAuthAccessTokenRequest;
import com.hyun.oauthboard.domain.dto.github.OAuthTokensResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Controller
public class AuthController {
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_MEMBER_INFO_URL = "https://api.github.com/user";
    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @GetMapping("/login/oauth2/code/github")
    public ResponseEntity<GithubMemberInfo> getMemberGithubInfo(@RequestParam final String code) {
        final OAuthTokensResponse gitAccessToken = getTokensInfo(code);
        final GithubMemberInfo githubMemberInfo = getGithubUserInfo(gitAccessToken.getAccessToken());
        return ResponseEntity.ok().body(githubMemberInfo);
    }

    public OAuthTokensResponse getTokensInfo(final String code) {
        return restTemplate.postForObject(
            ACCESS_TOKEN_URL,
            new OAuthAccessTokenRequest(clientId, clientSecret, code),
            OAuthTokensResponse.class
        );
    }

    public GithubMemberInfo getGithubUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<GithubMemberInfo> response = restTemplate.exchange(
            GITHUB_MEMBER_INFO_URL,
            HttpMethod.GET,
            request,
            GithubMemberInfo.class
        );

        return response.getBody();
    }
}

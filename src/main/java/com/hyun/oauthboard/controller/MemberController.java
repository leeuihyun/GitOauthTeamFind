package com.hyun.oauthboard.controller;

import com.hyun.oauthboard.domain.dto.github.GithubMemberInfo;
import com.hyun.oauthboard.domain.dto.github.OAuthAccessTokenRequest;
import com.hyun.oauthboard.domain.dto.github.OAuthTokensResponse;
import com.hyun.oauthboard.domain.dto.jwt.JwtToken;
import com.hyun.oauthboard.domain.entity.Member;
import com.hyun.oauthboard.jwt.JwtPayload;
import com.hyun.oauthboard.jwt.JwtTokenProvider;
import com.hyun.oauthboard.service.JwtBlacklistService;
import com.hyun.oauthboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_MEMBER_INFO_URL = "https://api.github.com/user";
    private static final RestTemplate restTemplate = new RestTemplate();
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtBlacklistService jwtBlacklistService;

    @Value("${security.github.client.client-id}")
    private String clientId;
    @Value("${security.github.client.client-secret}")
    private String clientSecret;

    @GetMapping("/login")
    public String logInPage() {

        return "login";
    }

    @GetMapping("/githublogin")
    public String githubLogin() {

        return "githublogin";
    }

    @GetMapping("/test")
    public String test(Authentication authentication) {

        return "test";
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal JwtPayload jwtPayload,
        @PathVariable final Long memberId) {

        memberService.deleteMember(jwtPayload, memberId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/member")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String bearerToken) {

        jwtBlacklistService.addBlackList(jwtTokenProvider.resolveToken(bearerToken));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/github/oauth")
    public ResponseEntity<JwtToken> getMemberGithubInfo(@RequestParam final String code) {

        final OAuthTokensResponse gitAccessToken = getTokensInfo(code);
        final GithubMemberInfo githubMemberInfo = getGithubUserInfo(
            gitAccessToken.getAccessToken());

        Member member = memberService.signIn(githubMemberInfo);

        JwtPayload jwtPayload = new JwtPayload(
            member.getMemberId(),
            member.getMemberName(),
            member.getMemberAvatar());

        return ResponseEntity.ok().body(jwtTokenProvider.generateToken(jwtPayload));
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

package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.github.GithubMemberInfo;
import com.hyun.oauthboard.domain.entity.Member;
import com.hyun.oauthboard.jwt.JwtPayload;
import org.springframework.security.core.Authentication;

public interface MemberService {

    Member signIn(GithubMemberInfo githubMemberInfo);

    void deleteMember(JwtPayload jwtPayload, Long memberId);

    Authentication authenticate(Member member);
}

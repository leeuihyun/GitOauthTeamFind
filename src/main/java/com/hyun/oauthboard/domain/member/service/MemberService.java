package com.hyun.oauthboard.domain.member.service;

import com.hyun.oauthboard.domain.member.dto.GithubMemberInfo;
import com.hyun.oauthboard.domain.member.entity.Member;
import com.hyun.oauthboard.jwt.JwtPayload;
import org.springframework.security.core.Authentication;

public interface MemberService {

    Member signIn(GithubMemberInfo githubMemberInfo);

    void deleteMember(JwtPayload jwtPayload, Long memberId);

    Authentication authenticate(Member member);
}

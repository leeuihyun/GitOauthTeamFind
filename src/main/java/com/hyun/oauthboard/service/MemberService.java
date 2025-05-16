package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.github.GithubMemberInfo;
import com.hyun.oauthboard.domain.entity.Member;
import org.springframework.security.core.Authentication;

public interface MemberService {

    Member signIn(GithubMemberInfo githubMemberInfo);

    Authentication authenticate(Member member);
}

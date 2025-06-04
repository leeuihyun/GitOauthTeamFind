package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.github.GithubMemberInfo;
import com.hyun.oauthboard.domain.entity.Member;
import com.hyun.oauthboard.enums.MemberError;
import com.hyun.oauthboard.exception.CustomException;
import com.hyun.oauthboard.jwt.JwtPayload;
import com.hyun.oauthboard.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Member signIn(GithubMemberInfo githubMemberInfo) {
        Optional<Member> optionalMember = memberRepository.findById(githubMemberInfo.getId());

        return optionalMember.orElseGet(() -> memberRepository.save(new Member(githubMemberInfo)));
    }

    @Transactional
    @Override
    public void deleteMember(JwtPayload jwtPayload, Long memberId) {
        Member member = memberRepository.findById(jwtPayload.getMemberId())
            .orElseThrow(() -> new CustomException(MemberError.MEMBER_ID_NOT_EXIST));

        if (!member.getMemberId().equals(memberId)) {
            throw new CustomException(MemberError.MEMBER_ID_MISMATCH);
        }

        memberRepository.delete(member);
    }

    @Override
    public Authentication authenticate(Member member) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
            member.getMemberId(), null, authorities);
    }
}


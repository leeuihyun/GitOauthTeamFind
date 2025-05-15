package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.github.GithubMemberInfo;
import com.hyun.oauthboard.domain.entity.Member;
import com.hyun.oauthboard.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Member signIn(GithubMemberInfo githubMemberInfo) {
        Optional<Member> optionalMember = memberRepository.findById(githubMemberInfo.getId());
        if (optionalMember.isPresent()) {
            return optionalMember.get();
        } else {
            Member newMember = new Member(githubMemberInfo);
            return memberRepository.save(newMember);
        }
    }

    @Override
    public Authentication authenticate(Member member) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            member.getMemberId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}


package com.hyun.oauthboard.domain.entity;

import com.hyun.oauthboard.domain.dto.github.GithubMemberInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_avatar")
    private String memberAvatar;

    public Member(GithubMemberInfo githubMemberInfo) {
        this.memberId = githubMemberInfo.getId();
        this.memberName = githubMemberInfo.getName();
        this.memberAvatar = githubMemberInfo.getAvatarUrl();
    }
}

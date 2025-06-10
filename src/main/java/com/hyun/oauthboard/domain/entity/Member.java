package com.hyun.oauthboard.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyun.oauthboard.domain.dto.github.GithubMemberInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Builder
@ToString(exclude = "boards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_avatar")
    private String memberAvatar;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Member(GithubMemberInfo githubMemberInfo) {
        this.memberId = githubMemberInfo.getId();
        this.memberName = githubMemberInfo.getName();
        this.memberAvatar = githubMemberInfo.getAvatarUrl();
    }
}

package com.hyun.oauthboard.domain.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GithubMemberInfo {
    private String login;
    private Long id;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private String name;
}

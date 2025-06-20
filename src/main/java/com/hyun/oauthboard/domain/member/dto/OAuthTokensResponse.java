package com.hyun.oauthboard.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthTokensResponse {

    @JsonProperty("access_token")
    private String accessToken;

}

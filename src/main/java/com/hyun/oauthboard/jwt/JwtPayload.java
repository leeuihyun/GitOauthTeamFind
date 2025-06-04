package com.hyun.oauthboard.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtPayload {

    private Long memberId;
    private String memberName;
    private String memberAvatar;
}

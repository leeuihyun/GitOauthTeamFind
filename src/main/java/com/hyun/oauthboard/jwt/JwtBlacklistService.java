package com.hyun.oauthboard.jwt;

public interface JwtBlacklistService {

    void addBlackList(String token);

    boolean isBlackList(String token);
}

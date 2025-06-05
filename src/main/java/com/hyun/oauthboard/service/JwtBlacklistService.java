package com.hyun.oauthboard.service;

public interface JwtBlacklistService {

    void addBlackList(String token);

    boolean isBlackList(String token);
}

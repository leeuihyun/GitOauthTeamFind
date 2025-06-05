package com.hyun.oauthboard.service;

import com.hyun.oauthboard.jwt.JwtTokenProvider;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean isBlackList(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

    @Override
    public void addBlackList(String token) {
        redisTemplate.opsForValue()
            .set(token, "exist", jwtTokenProvider.getRemainingTime(token), TimeUnit.MILLISECONDS);
    }
}

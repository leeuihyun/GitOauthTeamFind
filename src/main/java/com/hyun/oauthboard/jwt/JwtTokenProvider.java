package com.hyun.oauthboard.jwt;

import com.hyun.oauthboard.domain.dto.jwt.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    private Key accessKey;
    private Key refreshKey;

    @Value("${jwt.secret.access}")
    private String accessKeyStr;

    @Value("${jwt.secret.refresh}")
    private String refreshKeyStr;

    @PostConstruct
    public void go() {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKeyStr));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKeyStr));
    }

    public JwtToken generateToken(JwtPayload jwtPayload) {
        long now = (new Date()).getTime();

        // Access Token 30분 유지
        Date accessTokenExpires = new Date(now + (60 * 30 * 1000L));
        String accessToken = Jwts.builder()
            .setSubject(String.valueOf(jwtPayload.getMemberId()))
            .claim("memberAvatar", jwtPayload.getMemberAvatar())
            .claim("memberName", jwtPayload.getMemberName())
            .setExpiration(accessTokenExpires)
            .signWith(accessKey, SignatureAlgorithm.HS256)
            .compact();

        // RefreshToken 24시간 유지
        Date refreshTokenExpires = new Date(now + (60 * 60 * 24 * 1000L));
        String refreshToken = Jwts.builder()
            .setSubject(String.valueOf(jwtPayload.getMemberId()))
            .setExpiration(refreshTokenExpires)
            .signWith(refreshKey, SignatureAlgorithm.HS256)
            .compact();

        return JwtToken.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public Authentication getAccessAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(accessKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

        JwtPayload jwtPayload = new JwtPayload(Long.parseLong(claims.getSubject()),
            claims.get("memberName", String.class), claims.get("memberAvatar", String.class));

        return new UsernamePasswordAuthenticationToken(jwtPayload, "",
            List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public boolean validateToken(String token) {
        try {
            // 검증
            Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

package com.hyun.oauthboard.domain.boardLike.service;

import com.hyun.oauthboard.domain.boardLike.repository.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
}

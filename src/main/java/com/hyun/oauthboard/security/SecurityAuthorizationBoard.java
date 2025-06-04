package com.hyun.oauthboard.security;

import com.hyun.oauthboard.domain.entity.Board;
import com.hyun.oauthboard.enums.BoardError;
import com.hyun.oauthboard.exception.CustomException;
import com.hyun.oauthboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityAuthorizationBoard {

    private final BoardRepository boardRepository;

    public boolean checkOwner(Authentication authentication, Long boardId) {
        Long userId = Long.parseLong(authentication.getName());
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(BoardError.BOARD_ID_NOT_EXIST));

        return board.getMember().getMemberId().equals(userId);
    }
}

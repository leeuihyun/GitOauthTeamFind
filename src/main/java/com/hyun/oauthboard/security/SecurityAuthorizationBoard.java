package com.hyun.oauthboard.security;

import com.hyun.oauthboard.domain.board.entity.Board;
import com.hyun.oauthboard.domain.board.repository.BoardRepository;
import com.hyun.oauthboard.enums.BoardError;
import com.hyun.oauthboard.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityAuthorizationBoard {

    private final BoardRepository boardRepository;

    public boolean checkOwner(Long userId, Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(BoardError.BOARD_ID_NOT_EXIST));

        return board.getMember().getMemberId().equals(userId);
    }
}

package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.board.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.dto.board.BoardResponse;
import org.springframework.security.core.Authentication;

public interface BoardService {

    BoardResponse createBoard(Authentication authentication,
        BoardCreateRequestDto boardCreateRequestDto);

    BoardResponse updateBoard(Authentication authentication,
        BoardCreateRequestDto boardCreateRequestDto);

    void deleteBoard(Authentication authentication, Long boardId);
}

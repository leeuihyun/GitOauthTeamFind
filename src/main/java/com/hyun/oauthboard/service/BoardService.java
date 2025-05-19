package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.entity.Board;
import org.springframework.security.core.Authentication;

public interface BoardService {

    Board createBoard(Authentication authentication, BoardCreateRequestDto boardCreateRequestDto);

    Board updateBoard(Authentication authentication, BoardCreateRequestDto boardCreateRequestDto);

    void deleteBoard(Authentication authentication, Long boardId);
}

package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.board.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.dto.board.BoardResponse;
import com.hyun.oauthboard.jwt.JwtPayload;

public interface BoardService {

    BoardResponse createBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto);

    BoardResponse updateBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto);

    void deleteBoard(JwtPayload jwtPayload, Long boardId);
}

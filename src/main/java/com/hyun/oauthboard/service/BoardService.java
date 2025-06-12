package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.board.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.dto.board.BoardIdReponse;
import com.hyun.oauthboard.domain.dto.board.BoardResponse;
import com.hyun.oauthboard.jwt.JwtPayload;

public interface BoardService {

    BoardResponse readBoard(JwtPayload jwtPayload, Long boardId);

    BoardIdReponse createBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto);

    BoardIdReponse updateBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto);

    void deleteBoard(JwtPayload jwtPayload, Long boardId);
}

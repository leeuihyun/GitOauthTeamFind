package com.hyun.oauthboard.domain.board.service;

import com.hyun.oauthboard.domain.board.dto.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.board.dto.BoardIdReponse;
import com.hyun.oauthboard.domain.board.dto.BoardResponse;
import com.hyun.oauthboard.jwt.JwtPayload;

public interface BoardService {

    BoardResponse readBoard(JwtPayload jwtPayload, Long boardId);

    BoardIdReponse createBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto);

    BoardIdReponse updateBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto);

    void deleteBoard(JwtPayload jwtPayload, Long boardId);
}

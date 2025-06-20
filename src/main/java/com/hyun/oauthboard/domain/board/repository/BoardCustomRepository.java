package com.hyun.oauthboard.domain.board.repository;

import com.hyun.oauthboard.domain.board.dto.BoardResponse;

public interface BoardCustomRepository {

    BoardResponse findBoardLeftJoinViewsAndLikes(Long boardId);
}

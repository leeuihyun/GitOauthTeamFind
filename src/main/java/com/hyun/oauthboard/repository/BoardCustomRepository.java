package com.hyun.oauthboard.repository;

import com.hyun.oauthboard.domain.dto.board.BoardResponse;

public interface BoardCustomRepository {

    BoardResponse findBoardLeftJoinViewsAndLikes(Long boardId);
}

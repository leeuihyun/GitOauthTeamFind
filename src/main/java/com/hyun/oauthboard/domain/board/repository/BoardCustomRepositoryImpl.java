package com.hyun.oauthboard.domain.board.repository;

import static com.hyun.oauthboard.domain.board.entity.QBoard.board;
import static com.hyun.oauthboard.domain.board.entity.QBoardLike.boardLike;
import static com.hyun.oauthboard.domain.board.entity.QBoardView.boardView;
import static com.hyun.oauthboard.domain.member.entity.QMember.member;

import com.hyun.oauthboard.domain.board.dto.BoardResponse;
import com.hyun.oauthboard.domain.board.entity.Board;
import com.hyun.oauthboard.enums.BoardError;
import com.hyun.oauthboard.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public BoardResponse findBoardLeftJoinViewsAndLikes(Long boardId) {
        Board findBoard = Optional.ofNullable(jpaQueryFactory
            .selectFrom(board)
            .join(board.member, member).fetchJoin()
            .where(board.boardId.eq(boardId))
            .fetchOne()).orElseThrow(() -> new CustomException(BoardError.BOARD_ID_NOT_EXIST));

        Long likes = Optional.ofNullable(jpaQueryFactory
            .select(boardLike.likeId.count())
            .from(boardLike)
            .where(boardLike.board.boardId.eq(boardId))
            .fetchOne()).orElse(0L);

        Long views = Optional.ofNullable(jpaQueryFactory
            .select(boardView.viewId.count())
            .from(boardView)
            .where(boardView.board.boardId.eq(boardId))
            .fetchOne()).orElse(0L);

        return BoardResponse.builder()
            .boardId(findBoard.getBoardId())
            .memberId(findBoard.getMember().getMemberId())
            .boardTitle(findBoard.getBoardTitle())
            .boardContent(findBoard.getBoardContent())
            .likes(likes)
            .views(views)
            .createdAt(findBoard.getCreatedAt())
            .updatedAt(findBoard.getUpdatedAt())
            .build();
    }
}

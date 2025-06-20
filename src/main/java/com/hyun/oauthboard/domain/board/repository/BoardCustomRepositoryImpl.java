package com.hyun.oauthboard.domain.board.repository;

import static com.hyun.oauthboard.domain.board.entity.QBoard.board;
import static com.hyun.oauthboard.domain.boardLike.entity.QBoardLike.boardLike;
import static com.hyun.oauthboard.domain.boardView.entity.QBoardView.boardView;
import static com.hyun.oauthboard.domain.member.entity.QMember.member;

import com.hyun.oauthboard.domain.board.dto.BoardResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public BoardResponse findBoardLeftJoinViewsAndLikes(Long boardId) {

        return jpaQueryFactory
            .select(
                Projections.constructor(BoardResponse.class,
                    board.boardId,
                    member.memberId,
                    board.boardTitle,
                    board.boardContent,
                    boardView.viewId.count().coalesce(0L),
                    boardLike.likeId.count().coalesce(0L),
                    board.createdAt,
                    board.updatedAt
                )
            )
            .from(board)
            .join(board.member, member)
            .leftJoin(boardLike).on(boardLike.board.boardId.eq(board.boardId))
            .leftJoin(boardView).on(boardView.board.boardId.eq(board.boardId))
            .where(board.boardId.eq(boardId))
            .groupBy(
                board.boardId,
                member.memberId,
                board.boardTitle,
                board.boardContent,
                board.createdAt,
                board.updatedAt
            )
            .fetchOne();
    }
}

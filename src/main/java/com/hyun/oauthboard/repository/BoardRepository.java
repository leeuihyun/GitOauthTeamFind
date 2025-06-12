package com.hyun.oauthboard.repository;

import com.hyun.oauthboard.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

//    @Query("SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.likes WHERE b.boardId = :boardId")
//    Optional<Board> findById(@Param("boardId") Long boardId);
}

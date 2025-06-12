package com.hyun.oauthboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hyun.oauthboard.QueryDslTestConfig;
import com.hyun.oauthboard.domain.entity.Board;
import com.hyun.oauthboard.domain.entity.BoardLike;
import com.hyun.oauthboard.domain.entity.BoardView;
import com.hyun.oauthboard.domain.entity.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@Import(QueryDslTestConfig.class)
public class BoardRepositoryTest {


    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    void 좋아요_조회수_연관_조회_테스트() {
        Member writer = memberRepository.save(
            Member.builder()
                .memberId(1L)
                .memberName("작성자")
                .memberAvatar("avatar")
                .build());

        Member someone = memberRepository.save(
            Member.builder()
                .memberId(2L)
                .memberName("작성자")
                .memberAvatar("avatar")
                .build());

        Member someone2 = memberRepository.save(
            Member.builder()
                .memberId(3L)
                .memberName("작성자")
                .memberAvatar("avatar")
                .build());

        em.persist(writer);
        em.persist(someone);
        em.persist(someone2);

        Board board = Board.builder()
            .boardTitle("제목")
            .boardContent("내용")
            .member(writer)
            .build();

        em.persist(board);

        BoardLike boardLike1 = BoardLike.builder()
            .board(board)
            .liker(someone)
            .build();

        BoardLike boardLike2 = BoardLike.builder()
            .board(board)
            .liker(someone2)
            .build();

        BoardView boardView = BoardView.builder()
            .viewer(someone)
            .board(board)
            .build();

        em.persist(boardLike1);
        em.persist(boardLike2);
        em.persist(boardView);

        em.flush();
        em.clear();

        var response = boardRepository.findBoardLeftJoinViewsAndLikes(board.getBoardId());

        assertThat(response.getBoardId()).isEqualTo(board.getBoardId());
        assertThat(response.getViews()).isEqualTo(1L);
        assertThat(response.getBoardTitle()).isEqualTo("제목");
        assertThat(response.getLikes()).isEqualTo(2L);
    }

    @Test
    void NPlus1_테스트() {
        Member writer = memberRepository.save(
            Member.builder()
                .memberId(1L)
                .memberName("작성자")
                .memberAvatar("avatar")
                .build());

        em.persist(writer);

        Board board = Board.builder()
            .boardTitle("제목")
            .boardContent("내용")
            .member(writer)
            .build();

        em.persist(board);

        em.flush();
        em.clear();

        Board findBoard = boardRepository.findById(1L).orElseThrow();

//        for (Board board : boards) {
//            System.out.println("좋아요 수: " + board.getLikes().size());
//        }
    }
}

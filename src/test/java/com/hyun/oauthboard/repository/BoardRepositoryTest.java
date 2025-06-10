package com.hyun.oauthboard.repository;

import com.hyun.oauthboard.domain.entity.Board;
import com.hyun.oauthboard.domain.entity.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

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

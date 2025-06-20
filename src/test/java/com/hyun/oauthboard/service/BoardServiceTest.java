package com.hyun.oauthboard.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.hyun.oauthboard.domain.board.dto.BoardResponse;
import com.hyun.oauthboard.domain.board.repository.BoardRepository;
import com.hyun.oauthboard.domain.board.service.BoardServiceImpl;
import com.hyun.oauthboard.domain.member.repository.MemberRepository;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    BoardServiceImpl boardService;

    @Test
    @DisplayName("단일 게시글 조회가 성공한다")
    void 게시글_조회_테스트() {
        //given
        Long boardId = 1L;
        Long memberId = 100L;
        BoardResponse mockResponse = BoardResponse.builder()
            .boardId(boardId)
            .memberId(memberId)
            .boardTitle("테스트 제목")
            .boardContent("테스트 내용")
            .likes(3L)
            .views(10L)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        given(boardRepository.findBoardLeftJoinViewsAndLikes(anyLong())).willReturn(mockResponse);

        //when

        BoardResponse boardResponse = boardService.readBoard(any(), boardId);

        //then

        Assertions.assertThat(boardResponse.getBoardId()).isEqualTo(mockResponse.getBoardId());
        Assertions.assertThat(boardResponse.getBoardTitle())
            .isEqualTo(mockResponse.getBoardTitle());

        verify(boardRepository, times(1)).findBoardLeftJoinViewsAndLikes(anyLong());
    }

}

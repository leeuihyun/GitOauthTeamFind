package com.hyun.oauthboard.domain.entity;

import com.hyun.oauthboard.domain.dto.board.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.dto.board.BoardResponse;
import com.hyun.oauthboard.domain.entity.model.BoardEntityModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BoardEntityModel {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "board_title")
    private String boardTitle;

    @Column(name = "board_content")
    private String boardContent;

    public void updateBoard(BoardCreateRequestDto boardCreateRequestDto) {
        this.boardTitle = boardCreateRequestDto.getBoardTitle();
        this.boardContent = boardCreateRequestDto.getBoardContent();
    }

    public BoardResponse toDto() {
        return BoardResponse.builder()
            .boardId(this.boardId)
            .memberId(this.getMember().getMemberId())
            .boardTitle(this.boardTitle)
            .boardContent(this.boardContent)
            .createdAt(this.getCreatedAt())
            .updatedAt(this.getUpdatedAt())
            .build();
    }
}

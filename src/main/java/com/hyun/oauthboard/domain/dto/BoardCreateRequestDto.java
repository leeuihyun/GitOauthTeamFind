package com.hyun.oauthboard.domain.dto;

import com.hyun.oauthboard.domain.entity.Board;
import com.hyun.oauthboard.domain.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardCreateRequestDto {

    private Long boardId; // 생성 시에는 사용 X 수정 시 사용

    @NotBlank
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    private String boardTitle;

    @NotBlank
    private String boardContent;

    public Board toEntity(Member member) {
        return Board.builder()
            .member(member)
            .boardTitle(boardTitle)
            .boardContent(boardContent)
            .build();
    }
}

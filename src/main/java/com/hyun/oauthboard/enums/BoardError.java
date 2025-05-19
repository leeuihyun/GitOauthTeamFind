package com.hyun.oauthboard.enums;

import com.hyun.oauthboard.enums.model.ErrorModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardError implements ErrorModel {
    BOARD_ID_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 글 인덱스입니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;
}

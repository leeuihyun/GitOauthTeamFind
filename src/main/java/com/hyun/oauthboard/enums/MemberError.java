package com.hyun.oauthboard.enums;

import com.hyun.oauthboard.enums.model.ErrorModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberError implements ErrorModel {
    MEMBER_ID_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 작성자 인덱스입니다."),
    MEMBER_ID_MISMATCH(HttpStatus.NOT_FOUND, "작성자만 수정할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;
}

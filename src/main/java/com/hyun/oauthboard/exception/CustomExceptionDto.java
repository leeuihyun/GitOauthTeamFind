package com.hyun.oauthboard.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomExceptionDto {

    private final String errorMessage;
    private final HttpStatus httpStatus;

    public CustomExceptionDto(CustomException ex) {
        this.errorMessage = ex.getErrorMessage();
        this.httpStatus = ex.getHttpStatus();
    }
}


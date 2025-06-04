package com.hyun.oauthboard.domain.dto;

import com.hyun.oauthboard.exception.CustomException;
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


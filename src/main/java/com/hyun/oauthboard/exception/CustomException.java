package com.hyun.oauthboard.exception;

import com.hyun.oauthboard.enums.model.ErrorModel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorMessage;

    public <T extends ErrorModel> CustomException(T errorEnum) {
        super(errorEnum.getErrorMessage());
        this.httpStatus = errorEnum.getHttpStatus();
        this.errorMessage = errorEnum.getErrorMessage();
    }

}

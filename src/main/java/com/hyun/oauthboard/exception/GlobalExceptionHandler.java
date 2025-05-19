package com.hyun.oauthboard.exception;

import com.hyun.oauthboard.domain.dto.CustomExceptionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionDto> handleRequestBodyException(
        CustomException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new CustomExceptionDto(ex));
    }

}


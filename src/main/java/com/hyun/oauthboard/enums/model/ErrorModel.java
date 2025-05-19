package com.hyun.oauthboard.enums.model;

import org.springframework.http.HttpStatus;

public interface ErrorModel {

    String getErrorMessage();

    HttpStatus getHttpStatus();
}


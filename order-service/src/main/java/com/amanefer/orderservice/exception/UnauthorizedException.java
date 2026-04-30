package com.amanefer.orderservice.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseCustomException {

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "Bad credentials");
    }
}

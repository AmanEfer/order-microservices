package com.amanefer.orderservice.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseCustomException {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "Bad request");
    }
}

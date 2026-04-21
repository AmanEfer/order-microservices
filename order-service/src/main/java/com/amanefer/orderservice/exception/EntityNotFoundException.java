package com.amanefer.orderservice.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseCustomException {

    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "User not found");
    }
}

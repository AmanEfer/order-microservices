package com.amanefer.orderservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseCustomException {

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "User not found");
    }
}

package com.amanefer.orderservice.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends BaseCustomException {

    public RoleNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "Role not found");
    }
}

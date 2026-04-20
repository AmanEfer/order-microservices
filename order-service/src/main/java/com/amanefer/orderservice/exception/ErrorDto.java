package com.amanefer.orderservice.exception;

public record ErrorDto(
        int status,
        String code,
        Object details
) {
}

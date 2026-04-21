package com.amanefer.orderservice.model.dto;

public record LoginRequest(
        String username,
        String password
) {
}

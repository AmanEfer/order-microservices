package com.amanefer.orderservice.user.model.dto;

public record RegisterRequest(
        String username,
        String password,
        String email
) {
}

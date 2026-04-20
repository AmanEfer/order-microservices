package com.amanefer.orderservice.model.dto;

public record UserRequest(
        String username,
        String password,
        String email
) {
}

package com.amanefer.orderservice.model.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}

package com.amanefer.orderservice.user.model.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}

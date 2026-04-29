package com.amanefer.orderservice.model.dto.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}

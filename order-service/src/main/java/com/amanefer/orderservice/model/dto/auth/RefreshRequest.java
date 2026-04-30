package com.amanefer.orderservice.model.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(

        @NotBlank(message = "Поле с refresh токеном не может быть пустым")
        String refreshToken
) {
}

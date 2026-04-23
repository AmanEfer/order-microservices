package com.amanefer.orderservice.user.model.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(

        @NotBlank(message = "Поле с refresh токеном не может быть пустым")
        String refreshToken
) {
}

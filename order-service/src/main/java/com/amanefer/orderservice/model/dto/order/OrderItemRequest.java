package com.amanefer.orderservice.model.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(

        @NotNull(message = "ID товара обязателен")
        @Positive(message = "ID товара может иметь только положительное значение")
        Long productId,

        @NotNull(message = "Поле с количеством товара не может быть пустым")
        @Positive(message = "Количество товара должно быть больше нуля")
        Integer quantity
) {
}

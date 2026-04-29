package com.amanefer.orderservice.model.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(

        @NotEmpty(message = "Список товаров не может быть пустым")
        @Valid
        List<OrderItemRequest> items
) {
}

package com.amanefer.orderservice.order.model.dto;

public record OrderItemRequest(
        Long productId,
        Integer quantity
) {
}

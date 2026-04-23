package com.amanefer.orderservice.order.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderResponse(
        Long orderId,
        Long userId,
        BigDecimal totalPrice,
        List<OrderItemResponse> items
) {
}

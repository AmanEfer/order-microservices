package com.amanefer.orderservice.order.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(
        Long productId,
        String name,
        Integer quantity,
        BigDecimal price,
        Integer sale,
        BigDecimal priceWithSale,
        BigDecimal totalPrice
) {
}

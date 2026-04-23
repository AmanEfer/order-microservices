package com.amanefer.orderservice.order.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(
        Long productId,
        Integer quantity,
        BigDecimal price,
        Integer sale,
        BigDecimal priceWithSale,
        BigDecimal totalPrice
) {
}

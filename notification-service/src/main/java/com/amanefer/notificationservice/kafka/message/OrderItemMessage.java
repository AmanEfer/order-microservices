package com.amanefer.notificationservice.kafka.message;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemMessage(
        Long productId,
        String name,
        Integer quantity,
        BigDecimal price,
        Integer sale,
        BigDecimal priceWithSale,
        BigDecimal lineTotal
) {
}

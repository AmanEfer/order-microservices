package com.amanefer.notificationservice.model.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String name,
        Integer quantity,
        BigDecimal price,
        Integer sale,
        BigDecimal priceWithSale,
        BigDecimal lineTotal
) {
}

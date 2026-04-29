package com.amanefer.orderservice.model.dto.order;

import java.math.BigDecimal;

public record OrderItem(
        Long productId,
        String name,
        Integer quantity,
        BigDecimal price,
        Integer sale,
        BigDecimal priceWithSale,
        BigDecimal lineTotal
) {
}

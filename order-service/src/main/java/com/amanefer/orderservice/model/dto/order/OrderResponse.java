package com.amanefer.orderservice.model.dto.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long orderId,
        Long userId,
        BigDecimal orderTotal,
        List<OrderItem> items
) {
}

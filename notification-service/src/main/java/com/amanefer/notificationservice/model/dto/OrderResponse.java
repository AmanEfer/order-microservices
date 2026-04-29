package com.amanefer.notificationservice.model.dto;

import com.amanefer.notificationservice.model.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long orderId,
        Long userId,
        BigDecimal orderTotal,
        List<OrderItem> items
) {
}

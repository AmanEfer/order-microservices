package com.amanefer.notificationservice.kafka.message;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderMessage(
        Long userId,
        BigDecimal orderTotal,
        List<OrderItemMessage> items
) {
}

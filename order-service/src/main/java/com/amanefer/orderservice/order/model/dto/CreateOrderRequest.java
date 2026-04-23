package com.amanefer.orderservice.order.model.dto;

import java.util.List;

public record CreateOrderRequest(
        List<OrderItemRequest> items
) {
}

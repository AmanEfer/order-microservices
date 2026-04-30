package com.amanefer.notificationservice.model.dto;

import java.util.List;

public record OrderItemPageResponse(
        List<OrderItemResponse> items,
        boolean first,
        boolean last,
        int number,
        int numberOfElements,
        int size,
        long totalElements,
        int totalPages
) {
}

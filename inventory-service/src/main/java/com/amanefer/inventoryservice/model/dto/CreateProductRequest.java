package com.amanefer.inventoryservice.model.dto;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        Integer quantity,
        BigDecimal price
) {
}

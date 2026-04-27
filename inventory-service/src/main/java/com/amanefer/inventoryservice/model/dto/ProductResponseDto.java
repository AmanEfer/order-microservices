package com.amanefer.inventoryservice.model.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        Integer quantity,
        BigDecimal price,
        Integer sale
) {
}

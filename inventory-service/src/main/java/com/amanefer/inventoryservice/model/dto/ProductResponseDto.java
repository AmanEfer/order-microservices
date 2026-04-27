package com.amanefer.inventoryservice.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponseDto(
        Long id,
        String name,
        Integer quantity,
        BigDecimal price,
        Integer sale
) {
}

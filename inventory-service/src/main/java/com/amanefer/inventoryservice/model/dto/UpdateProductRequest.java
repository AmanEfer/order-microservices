package com.amanefer.inventoryservice.model.dto;

import java.math.BigDecimal;

public record UpdateProductRequest(
        BigDecimal price
) {
}

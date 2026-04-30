package com.amanefer.inventoryservice.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    PRODUCT_NOT_FOUND(
            "Товара с ID %s нет на складе",
            HttpStatus.NOT_FOUND
    ),

    PRODUCT_NOT_ENOUGH(
            "На складе недостаточно товара с ID %d(%s). Попробуйте заказать меньше",
            HttpStatus.BAD_REQUEST
    );

    private final String message;
    private final HttpStatus status;
}

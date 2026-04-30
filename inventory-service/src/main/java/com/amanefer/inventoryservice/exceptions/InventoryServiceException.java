package com.amanefer.inventoryservice.exceptions;

import lombok.Getter;

@Getter
public class InventoryServiceException extends RuntimeException {

    private final ErrorType errorType;

    public InventoryServiceException(ErrorType errorType, Object... args) {
        super(errorType.getMessage().formatted(args));
        this.errorType = errorType;
    }
}

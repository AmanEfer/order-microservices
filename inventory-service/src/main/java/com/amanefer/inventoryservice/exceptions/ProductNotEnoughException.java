package com.amanefer.inventoryservice.exceptions;

public class ProductNotEnoughException extends RuntimeException {
    public ProductNotEnoughException(String message) {
        super(message);
    }
}

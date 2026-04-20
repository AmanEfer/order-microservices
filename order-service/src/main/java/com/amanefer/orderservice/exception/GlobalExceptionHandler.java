package com.amanefer.orderservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(EntityNotFoundException ex) {

        var errorDto = new ErrorDto(
                ex.getStatus().value(),
                ex.getCode(),
                ex.getMessage()
        );

        return ResponseEntity.status(ex.getStatus()).body(errorDto);
    }
}

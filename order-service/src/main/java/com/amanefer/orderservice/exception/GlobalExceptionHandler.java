package com.amanefer.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        var errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            RoleNotFoundException.class,
            UnauthorizedException.class,
            BadRequestException.class,
            InvalidTokenException.class
    })
    public ResponseEntity<ErrorDto> handleBaseCustomException(BaseCustomException ex) {
        var errorDto = new ErrorDto(
                ex.getStatus().value(),
                ex.getCode(),
                ex.getMessage()
        );

        return ResponseEntity.status(ex.getStatus()).body(errorDto);
    }
}

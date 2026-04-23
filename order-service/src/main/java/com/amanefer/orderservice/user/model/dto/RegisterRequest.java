package com.amanefer.orderservice.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Поле логина должно быть заполнено")
        String username,

        @NotBlank(message = "Поле с паролем должно быть заполнено")
        @Size(min = 3, max = 20, message = "Пароль должен содержать от 3 до 20 символов")
        @Pattern(
                regexp = "^[A-Za-z0-9!@#$%^&]+$",
                message = "Пароль может содержать цифры, латинские буквы и символы !@#$%^&"
        )
        String password,

        @NotBlank(message = "Поле email не может быть пустым")
        @Email(message = "Некорректный формат email")
        String email
) {
}

package com.amanefer.orderservice.model.dto;

import java.util.Set;

public record UserRequest(
        String username,
        String password,
        String email,
        Set<String> roles
) {
}

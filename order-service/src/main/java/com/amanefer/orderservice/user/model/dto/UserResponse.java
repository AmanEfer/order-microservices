package com.amanefer.orderservice.user.model.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record UserResponse(
        Long id,
        String username,
        String email,
        Set<RoleResponse> roles
) {
}

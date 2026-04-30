package com.amanefer.orderservice.model.dto.user;

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

package com.amanefer.orderservice.mapper;

import com.amanefer.orderservice.user.model.dto.RegisterRequest;
import com.amanefer.orderservice.user.model.dto.RoleResponse;
import com.amanefer.orderservice.user.model.dto.UserRequest;
import com.amanefer.orderservice.user.model.dto.UserResponse;
import com.amanefer.orderservice.user.model.entity.Role;
import com.amanefer.orderservice.user.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);

    RoleResponse toRoleResponse(Role role);

    List<UserResponse> toResponseList(List<User> users);
}

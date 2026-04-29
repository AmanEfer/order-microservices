package com.amanefer.orderservice.service;

import com.amanefer.orderservice.exception.RoleNotFoundException;
import com.amanefer.orderservice.model.entity.Role;
import com.amanefer.orderservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    @Value("${user.role.default-name}")
    private String defaultRoleName;

    private final RoleRepository roleRepository;

    public Set<Role> prepareRoles(Set<String> roles) {
        return roles == null || roles.isEmpty()
                ? Set.of(getRoleByName(defaultRoleName))
                : roles.stream()
                .map(this::getRoleByName)
                .collect(Collectors.toSet());
    }

    public Role getDefaultRole() {
        return getRoleByName(defaultRoleName);
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName.toUpperCase().trim())
                .orElseThrow(() ->
                        new RoleNotFoundException("Роли %s не существует".formatted(roleName)));
    }
}

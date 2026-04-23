package com.amanefer.orderservice.user.service;

import com.amanefer.orderservice.exception.RoleNotFoundException;
import com.amanefer.orderservice.user.model.entity.Role;
import com.amanefer.orderservice.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(String roleName) {

        return roleRepository.findByName(roleName.toUpperCase().trim())
                .orElseThrow(() ->
                        new RoleNotFoundException("Роли %s не существует".formatted(roleName)));
    }
}

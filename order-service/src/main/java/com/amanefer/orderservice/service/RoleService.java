package com.amanefer.orderservice.service;

import com.amanefer.orderservice.exception.EntityNotFoundException;
import com.amanefer.orderservice.model.entity.Role;
import com.amanefer.orderservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(String roleName) {

        return roleRepository.findByName(roleName.toUpperCase().trim())
                .orElseThrow(() ->
                        new EntityNotFoundException("Такой роли не существует"));
    }
}

package com.amanefer.orderservice.user.service;

import com.amanefer.orderservice.exception.BadRequestException;
import com.amanefer.orderservice.exception.UserNotFoundException;
import com.amanefer.orderservice.user.model.dto.UserRequest;
import com.amanefer.orderservice.user.model.entity.Role;
import com.amanefer.orderservice.user.model.entity.User;
import com.amanefer.orderservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${user.role.default-name}")
    private String defaultRoleName;

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createNewUser(UserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Такой email уже занят");
        }

        Set<Role> roles;
        if (request.roles() == null || request.roles().isEmpty()) {
            var role = roleService.getRoleByName(defaultRoleName);
            roles = Set.of(role);
        } else {
            roles = request.roles().stream()
                    .map(roleService::getRoleByName)
                    .collect(Collectors.toSet());
        }

        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("Пользователь с ID " + id + " не найден"));
    }

    @Transactional
    public String deleteUserById(Long id) {
        var userForDelete = getUserById(id);

        userRepository.delete(userForDelete);

        return "Пользователь с ID " + id + " успешно удален";
    }
}

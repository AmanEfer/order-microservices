package com.amanefer.orderservice.service;

import com.amanefer.orderservice.exception.EntityNotFoundException;
import com.amanefer.orderservice.model.dto.UserRequest;
import com.amanefer.orderservice.model.entity.User;
import com.amanefer.orderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
        var role = roleService.getRoleByName(defaultRoleName);

        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .roles(Set.of(role))
                .build();

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Пользователь с ID " + id + " не найден"));
    }

    @Transactional
    public String deleteUserById(Long id) {
        var userForDelete = getUserById(id);
        userRepository.delete(userForDelete);

        return "Пользователь с ID " + id + " успешно удален";
    }
}

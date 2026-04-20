package com.amanefer.orderservice.service;

import com.amanefer.orderservice.exception.EntityNotFoundException;
import com.amanefer.orderservice.model.dto.UserRequest;
import com.amanefer.orderservice.model.entity.User;
import com.amanefer.orderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${user.role.default-name}")
    private String roleName;

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Transactional
    @Override
    public User createNewUser(UserRequest request) {

        var role = roleService.getRoleByName(roleName);

        var user = User.builder()
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .roles(Set.of(role))
                .build();

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Пользователь с ID " + id + " не найден"));
    }

    @Transactional
    @Override
    public String deleteUserById(Long id) {

        var userForDelete = getUserById(id);
        userRepository.delete(userForDelete);

        return "Пользователь с ID " + id + " успешно удален";
    }
}

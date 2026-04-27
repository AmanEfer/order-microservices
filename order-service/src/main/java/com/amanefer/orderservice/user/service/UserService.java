package com.amanefer.orderservice.user.service;

import com.amanefer.orderservice.exception.BadRequestException;
import com.amanefer.orderservice.exception.UserNotFoundException;
import com.amanefer.orderservice.mapper.UserMapper;
import com.amanefer.orderservice.user.model.dto.UserRequest;
import com.amanefer.orderservice.user.model.dto.UserResponse;
import com.amanefer.orderservice.user.model.entity.User;
import com.amanefer.orderservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse createNewUser(UserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Такой email уже занят");
        }

        var user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleService.prepareRoles(request.roles()));

        var savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        var users = userRepository.findAll();

        return userMapper.toResponseList(users);
    }

    public UserResponse getUserById(Long id) {
        var user = getUser(id);

        return userMapper.toResponse(user);
    }

    @Transactional
    public String deleteUserById(Long id) {
        var userForDelete = getUser(id);

        userRepository.delete(userForDelete);

        return "Пользователь с ID " + id + " успешно удален";
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("Пользователь с ID " + id + " не найден"));
    }
}

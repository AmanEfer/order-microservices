package com.amanefer.orderservice.service;

import com.amanefer.orderservice.exception.UserNotFoundException;
import com.amanefer.orderservice.mapper.UserMapper;
import com.amanefer.orderservice.model.dto.user.UserRequest;
import com.amanefer.orderservice.model.dto.user.UserResponse;
import com.amanefer.orderservice.model.entity.User;
import com.amanefer.orderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRegistrationService userRegistrationService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public UserResponse createNewUser(UserRequest request) {
        var user = userRegistrationService.prepareUserForSave(
                request.username(),
                request.password(),
                request.email(),
                roleService.prepareRoles(request.roles())
        );

        var savedUser = userRegistrationService.saveNewUser(user);

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

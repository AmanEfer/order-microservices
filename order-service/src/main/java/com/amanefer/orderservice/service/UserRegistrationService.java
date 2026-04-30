package com.amanefer.orderservice.service;

import com.amanefer.orderservice.exception.BadRequestException;
import com.amanefer.orderservice.model.entity.Role;
import com.amanefer.orderservice.model.entity.User;
import com.amanefer.orderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User saveNewUser(User user) {
        return userRepository.save(user);
    }

    public User prepareUserForSave(
            String username,
            String password,
            String email,
            Set<Role> roles
    ) {
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Такой email уже занят");
        }

        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .roles(roles)
                .build();
    }
}

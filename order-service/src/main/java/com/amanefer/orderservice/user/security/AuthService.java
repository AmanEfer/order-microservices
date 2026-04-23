package com.amanefer.orderservice.user.security;

import com.amanefer.orderservice.exception.BadRequestException;
import com.amanefer.orderservice.exception.InvalidTokenException;
import com.amanefer.orderservice.exception.UnauthorizedException;
import com.amanefer.orderservice.user.model.dto.AuthResponse;
import com.amanefer.orderservice.user.model.dto.LoginRequest;
import com.amanefer.orderservice.user.model.dto.RegisterRequest;
import com.amanefer.orderservice.user.model.entity.User;
import com.amanefer.orderservice.user.repository.UserRepository;
import com.amanefer.orderservice.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${user.role.default-name}")
    private String defaultRoleName;

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Такой email уже занят");
        }

        var role = roleService.getRoleByName(defaultRoleName);
        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .roles(Set.of(role))
                .build();

        userRepository.save(user);

        return generateTokensAndCreateAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        var user = getUser(request.username(), "Неправильный логин или пароль");

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Неправильный логин или пароль");
        }

        return generateTokensAndCreateAuthResponse(user);
    }

    public AuthResponse refresh(String refreshToken) {
        var username = jwtService.extractUsername(refreshToken, true);
        var user = getUser(username, "Пользователь не найден");
        var userDetails = new CustomUserDetails(user);

        if (!jwtService.isRefreshTokenValid(refreshToken, userDetails)) {
            throw new InvalidTokenException("Refresh токен не валиден");
        }

        return generateTokensAndCreateAuthResponse(user);
    }

    private User getUser(String username, String message) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException(message));
    }

    private AuthResponse generateTokensAndCreateAuthResponse(User user) {
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken);
    }
}

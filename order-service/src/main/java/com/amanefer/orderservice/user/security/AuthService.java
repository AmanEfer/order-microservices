package com.amanefer.orderservice.user.security;

import com.amanefer.orderservice.exception.BadRequestException;
import com.amanefer.orderservice.exception.InvalidTokenException;
import com.amanefer.orderservice.exception.UnauthorizedException;
import com.amanefer.orderservice.exception.UserNotFoundException;
import com.amanefer.orderservice.mapper.UserMapper;
import com.amanefer.orderservice.user.model.dto.AuthResponse;
import com.amanefer.orderservice.user.model.dto.LoginRequest;
import com.amanefer.orderservice.user.model.dto.RegisterRequest;
import com.amanefer.orderservice.user.model.entity.User;
import com.amanefer.orderservice.user.repository.UserRepository;
import com.amanefer.orderservice.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Такой email уже занят");
        }

        var user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(roleService.getDefaultRole()));

        userRepository.save(user);

        return generateTokensAndCreateAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UnauthorizedException("Неправильный логин или пароль"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Неправильный логин или пароль");
        }

        return generateTokensAndCreateAuthResponse(user);
    }

    public AuthResponse refresh(String refreshToken) {
        var claims = jwtService.extractClaims(refreshToken, true);
        Long userId = claims.get("userId", Long.class);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        var userDetails = new CustomUserDetails(user);

        if (!jwtService.isTokenValid(claims, userDetails)) {
            throw new InvalidTokenException("Refresh токен не валиден");
        }

        return generateTokensAndCreateAuthResponse(user);
    }

    private AuthResponse generateTokensAndCreateAuthResponse(User user) {
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken);
    }
}

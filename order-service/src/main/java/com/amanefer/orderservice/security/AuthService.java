package com.amanefer.orderservice.security;

import com.amanefer.orderservice.exception.InvalidTokenException;
import com.amanefer.orderservice.exception.UnauthorizedException;
import com.amanefer.orderservice.exception.UserNotFoundException;
import com.amanefer.orderservice.model.dto.auth.AuthResponse;
import com.amanefer.orderservice.model.dto.auth.LoginRequest;
import com.amanefer.orderservice.model.dto.auth.RegisterRequest;
import com.amanefer.orderservice.model.entity.User;
import com.amanefer.orderservice.repository.UserRepository;
import com.amanefer.orderservice.service.RoleService;
import com.amanefer.orderservice.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRegistrationService userRegistrationService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        var user = userRegistrationService.prepareUserForSave(
                request.username(),
                request.password(),
                request.email(),
                Set.of(roleService.getDefaultRole())
        );

        userRegistrationService.saveNewUser(user);

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

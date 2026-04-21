package com.amanefer.orderservice.controller;

import com.amanefer.orderservice.model.dto.AuthResponse;
import com.amanefer.orderservice.model.dto.LoginRequest;
import com.amanefer.orderservice.model.dto.RefreshRequest;
import com.amanefer.orderservice.model.dto.RegisterRequest;
import com.amanefer.orderservice.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        var response = authService.register(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        var response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        var response = authService.refresh(request.refreshToken());

        return ResponseEntity.ok(response);
    }
}

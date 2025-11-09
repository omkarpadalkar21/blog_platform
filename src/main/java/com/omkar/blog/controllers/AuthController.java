package com.omkar.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omkar.blog.domain.dtos.AuthResponse;
import com.omkar.blog.domain.dtos.LoginRequest;
import com.omkar.blog.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String tokenValue = authService.generateToken(userDetails);

        AuthResponse authResponse = AuthResponse.builder()
                .token(tokenValue)
                .expiresIn(86400)
                .build();

        return ResponseEntity.ok(authResponse);
    }
}

package com.omkar.blog.services.impl;

import com.omkar.blog.domain.entities.User;
import com.omkar.blog.repositories.UserRepository;
import com.omkar.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(cacheNames = "users",key = "#userId")
    public User getUserById(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }
}

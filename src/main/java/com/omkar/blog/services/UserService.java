package com.omkar.blog.services;

import com.omkar.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {
     User getUserById(UUID userId);
}

package com.omkar.blog.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserDetails authenticate(String email, String password);
    String generateToken(UserDetails userDetails);
}

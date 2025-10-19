package com.omkar.blog.security;

import com.omkar.blog.domain.entities.User;
import com.omkar.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * This service is responsible for loading user details during authentication.
 * It bridges the gap between Spring Security and our custom User entity.
 */
@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user details by email address for Spring Security authentication.
     * 
     * This method is called by Spring Security during the authentication process
     * when a user attempts to log in. It retrieves the user from the database
     * using the provided email and wraps it in a BlogUserDetails object that
     * implements Spring Security's UserDetails interface.
     * 
     * @param email the email address of the user to load
     * @return UserDetails object containing user information for authentication
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Retrieve user from database by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        // Wrap the User entity in BlogUserDetails for Spring Security
        return new BlogUserDetails(user);
    }
}

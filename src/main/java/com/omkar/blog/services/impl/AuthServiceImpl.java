package com.omkar.blog.services.impl;

import com.omkar.blog.services.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of authentication service that handles user authentication and JWT token management.
 * This service provides methods for authenticating users, generating JWT tokens, and validating tokens.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;

    private final Long JwtExpiryMs = 86400000L;

    /**
     * Authenticates a user with the provided email and password.
     * 
     * This method uses Spring Security's AuthenticationManager to verify the user's credentials.
     * If authentication is successful, it returns the UserDetails object containing user information.
     * If authentication fails, Spring Security will throw an AuthenticationException.
     * 
     * @param email the user's email address
     * @param password the user's password
     * @return UserDetails object containing authenticated user information
     * @throws org.springframework.security.core.AuthenticationException if authentication fails
     */
    @Override
    public UserDetails authenticate(String email, String password) {
        // Authenticate user credentials using Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        // Load and return user details after successful authentication
        return userDetailsService.loadUserByUsername(email);
    }

    /**
     * Generates a JWT token for the authenticated user.
     * 
     * This method creates a JSON Web Token containing user information and signs it with HMAC SHA-256.
     * The token includes the user's username as the subject, issue time, and expiration time.
     * The token expires after 24 hours (86400000 milliseconds).
     * 
     * @param userDetails the authenticated user's details
     * @return a signed JWT token string
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        // Create empty claims map for additional token data
        Map<String, Object> claims = new HashMap<>();
        
        // Build and sign the JWT token
        return Jwts.builder()
                .addClaims(claims)                                    // Add custom claims
                .setSubject(userDetails.getUsername())                // Set user email as subject
                .setIssuedAt(new Date(System.currentTimeMillis()))    // Set token creation time
                .setExpiration(new Date(System.currentTimeMillis() + JwtExpiryMs))  // Set expiration (24 hours)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sign with HMAC SHA-256
                .compact();                                           // Generate compact token string
    }

    /**
     * Validates a JWT token and returns the associated user details.
     * 
     * This method extracts the username from the token, validates the token's signature,
     * and returns the UserDetails object for the user. If the token is invalid or expired,
     * JWT parsing will throw an exception.
     * 
     * @param token the JWT token to validate
     * @return UserDetails object for the user associated with the token
     * @throws io.jsonwebtoken.JwtException if the token is invalid, expired, or malformed
     */
    @Override
    public UserDetails validateToken(String token) {
        // Extract username from token
        String username = extractUsername(token);
        // Load and return user details for the extracted username
        return userDetailsService.loadUserByUsername(username);
    }

    /**
     * Extracts the username (email) from a JWT token.
     * 
     * This private method parses the JWT token, verifies its signature, and extracts
     * the subject claim which contains the user's email address.
     * 
     * @param token the JWT token to parse
     * @return the username (email) extracted from the token
     * @throws io.jsonwebtoken.JwtException if the token is invalid or malformed
     */
    private String extractUsername(String token) {
        // Parse and validate the JWT token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // Use the same signing key for verification
                .build()
                .parseClaimsJws(token)           // Parse the token and verify signature
                .getBody();                      // Get the claims (payload) from the token

        // Return the subject claim which contains the username
        return claims.getSubject();
    }

    /**
     * Creates a signing key from the configured secret key for JWT operations.
     * 
     * This method converts the secret key string into a Key object suitable for
     * HMAC SHA-256 signing and verification operations.
     * 
     * @return a Key object for JWT signing and verification
     */
    private Key getSigningKey() {
        // Convert secret key string to bytes
        byte[] keyBytes = secretKey.getBytes();
        // Create HMAC SHA-256 key from the bytes
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

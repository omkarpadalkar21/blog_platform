package com.omkar.blog.security;

import com.omkar.blog.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
A Spring Security filter that runs once per request, extracts a Bearer JWT from the Authorization header,
asks AuthService to validate/parse it into UserDetails, and sets a UsernamePasswordAuthenticationToken into
the SecurityContextHolder so the request is treated as an authenticated user.*/
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);
            if (token != null) {
                UserDetails userDetails = authService.validateToken(token);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication); // makes our authentication
                // object available for the rest of the request

                if (userDetails instanceof BlogUserDetails blogUserDetails) {
                    request.setAttribute("userId", blogUserDetails.getId());
                }

            }
        } catch (Exception e) {
            // Don't throw exception, just don't authenticate the user
            log.warn("Received invalid auth token");
        }

        filterChain.doFilter(request, response); // marks the job of this filter has been completed and forwards it
        //to the next filter or controller. If not done, the request stops here and the next controllers will never
        //be called.
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}

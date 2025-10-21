### Step 1: Security configuration
Create the Spring Security configuration with stateless session management and request authorization rules: [SecurityConfig](src/main/java/com/omkar/blog/config/SecurityConfig.java)

### Step 2: Password encoder
Expose a `PasswordEncoder` bean for hashing and verifying passwords in the authentication flow (see `SecurityConfig`).

### Step 3: Domain-to-security adapter
Create a `UserDetails` implementation to adapt your domain `User` to Spring Security: [BlogUserDetails](src/main/java/com/omkar/blog/security/BlogUserDetails.java)

### Step 4: User lookup service
Provide a `UserDetailsService` that loads users (by email) from the database: [BlogUserDetailsService](src/main/java/com/omkar/blog/security/BlogUserDetailsService.java)

### Step 5: Authentication provider and manager
Register a `DaoAuthenticationProvider` wired with the `UserDetailsService` and `PasswordEncoder`, and expose the `AuthenticationManager` (see `SecurityConfig`).

### Step 6: Auth service and DTOs
Create DTOs and the auth service surface: [LoginRequest](src/main/java/com/omkar/blog/domain/dtos/LoginRequest.java), [AuthResponse](src/main/java/com/omkar/blog/domain/dtos/AuthResponse.java), [AuthService](src/main/java/com/omkar/blog/services/AuthService.java). Implement authentication and JWT management: [AuthServiceImpl](src/main/java/com/omkar/blog/services/impl/AuthServiceImpl.java)

### Step 7 (optional for JWT): Request filtering
Add a JWT authentication filter to validate tokens and establish security context on each request, then register it in the filter chain before `UsernamePasswordAuthenticationFilter` (e.g., `JwtAuthFilter`).
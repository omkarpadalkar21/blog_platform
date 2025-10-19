### Step 1: create a security config: [SecurityConfig](src/main/java/com/omkar/blog/config/SecurityConfig.java)

### Step 2: Create a UserDetails class implementing UserDetails interface: [BlogUserDetails](src/main/java/com/omkar/blog/security/BlogUserDetails.java)

### Step 3: Create a UserDetailsService which is used by Spring Security to lookup users: [BlogUserDetailsService](src/main/java/com/omkar/blog/security/BlogUserDetailsService.java)

### Step 4: Create class for LoginRequest, AuthResponse, and Interface for AuthService: [LoginRequest](src/main/java/com/omkar/blog/domain/dtos/LoginRequest.java), [AuthResponse](src/main/java/com/omkar/blog/domain/dtos/AuthResponse.java), [AuthService](src/main/java/com/omkar/blog/services/AuthService.java), Implement AuthService that handles user authentication and JWT token management [AuthServiceImpl](src/main/java/com/omkar/blog/services/impl/AuthServiceImpl.java)

### Step 5: Create JWT Filter class which enables stateless auth by validating tokens and establishing user context for each req: [JwtAuthFilter](src/main/java/com/omkar/blog/security/JwtAuthFilter.java)
### Step 1: create a security config: [SecurityConfig](src/main/java/com/omkar/blog/config)

### Step 2: Create a UserDetails class implementing UserDetails interface: [BlogUserDetails](src/main/java/com/omkar/blog/security/BlogUserDetails.java)

### Step 3: Create a UserDetailsService which is used by Spring Security to lookup users: [BlogUserDetailsService](src/main/java/com/omkar/blog/security/BlogUserDetailsService.java)

### Step 4: Create class for LoginRequest, AuthResponse, and Interface for AuthService: [LoginRequest](src/main/java/com/omkar/blog/domain/dtos/LoginRequest.java), [AuthResponse](src/main/java/com/omkar/blog/domain/dtos/AuthResponse.java), [AuthService](src/main/java/com/omkar/blog/services/AuthService.java), [AuthServiceImpl]()

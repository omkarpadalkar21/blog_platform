# Blog Platform - Spring Security Practice Project

This is a **practice project** created to learn and implement Spring Security with JWT authentication in a Spring Boot application. It demonstrates building a secure RESTful API for a blog platform.

> **Note:** This is not a production-ready application. It's a learning exercise focused on understanding Spring Security concepts, JWT tokens, authentication, and authorization.

## 🎯 Project Purpose

This project serves as a hands-on learning experience for:
- Implementing JWT-based authentication and authorization
- Configuring Spring Security for stateless REST APIs
- Building custom security filters
- Understanding authentication providers and managers
- Working with Spring Security's `UserDetailsService` and `UserDetails`

## 🛠️ Tech Stack

- **Framework:** Spring Boot 3.5.6
- **Security:** Spring Security with JWT (JJWT 0.11.5)
- **Database:** PostgreSQL (via Docker)
- **ORM:** Spring Data JPA (Hibernate)
- **Mapping:** MapStruct
- **Build Tool:** Maven
- **Java Version:** 17
- **Libraries:** Lombok, Validation

## 📁 Project Structure

```
src/main/java/com/omkar/blog/
├── config/          # Spring Security configuration
├── controllers/      # REST API endpoints
├── domain/          # Entities, DTOs, and domain objects
├── mappers/         # MapStruct mappers
├── repositories/    # JPA repositories
├── security/        # Security-related classes (UserDetails, filters)
└── services/        # Business logic
```

## 🔐 Key Security Features Implemented

1. **JWT Authentication**
   - Token-based stateless authentication
   - Custom JWT authentication filter
   - Token validation on every request

2. **Authentication Flow**
   - User registration (email, password)
   - Login endpoint that returns JWT tokens
   - Password encoding with BCrypt

3. **Authorization**
   - Public endpoints: Login, GET posts/categories/tags, view published posts
   - Protected endpoints: Create/update posts, view drafts, CRUD operations on categories and tags
   - Stateless authentication with JWT tokens

4. **Custom Security Components**
   - `BlogUserDetails`: Adapts domain `User` to Spring Security's `UserDetails`
   - `BlogUserDetailsService`: Loads user by email for authentication
   - `JwtAuthFilter`: Validates JWT tokens and sets security context
   - `AuthService`: Handles authentication and token generation

## 🗄️ Database Schema

- **Users**: id, email, password, name, createdAt
- **Posts**: id, title, content, status (DRAFT/PUBLISHED), readingTime, createdAt, updatedAt
- **Categories**: id, name, description
- **Tags**: id, name
- **Relationships**: User → Posts (one-to-many), Post → Category (many-to-one), Post ↔ Tags (many-to-many)

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Docker and Docker Compose

### Setup Instructions

1. **Clone the repository**

2. **Start PostgreSQL database**
   ```bash
   docker-compose up -d
   ```
   This starts PostgreSQL on port 5432 and Adminer (database management UI) on port 8888.

3. **Configure the application**
   
   Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=changemeinprod!
   
   jwt.secret=your-256-bit-secret-key-minimum-32-characters-long
   ```

4. **Build the project**
   ```bash
   ./mvnw clean install
   ```

5. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

6. **Test the API**
   
   The application runs on `http://localhost:8080`
   
   **Login** (returns JWT token):
   ```bash
   curl -X POST http://localhost:8080/api/v1/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email":"omkar@gmail.com","password":"12345"}'
   ```
   
   Save the token from the response.
   
   **Get all posts** (public):
   ```bash
   curl http://localhost:8080/api/v1/posts
   ```
   
   **Get your drafts** (requires authentication):
   ```bash
   curl http://localhost:8080/api/v1/posts/drafts \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"
   ```
   
   **Create a category** (requires authentication):
   ```bash
   curl -X POST http://localhost:8080/api/v1/categories \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     -d '{"name":"Technology","description":"Tech related posts"}'
   ```

## 📝 API Endpoints

### Authentication
- `POST /api/v1/auth/login` - Login and get JWT token (public)

### Posts
- `GET /api/v1/posts` - Get all posts (public)
- `GET /api/v1/posts/drafts` - Get user's draft posts (authenticated)
- `POST /api/v1/posts` - Create a new post (authenticated)
- `PUT /api/v1/posts/{id}` - Update existing post (authenticated)

### Categories
- `GET /api/v1/categories` - List all categories (public)
- `POST /api/v1/categories` - Create a new category (authenticated)
- `DELETE /api/v1/categories/{id}` - Delete a category (authenticated)

### Tags
- `GET /api/v1/tags` - Get all tags (public)
- `POST /api/v1/tags` - Create tags (authenticated)
- `DELETE /api/v1/tags/{id}` - Delete a tag (authenticated)

## 🔍 Key Learning Points

Through this project, you can learn:

1. **Spring Security Configuration**: How to configure stateless authentication with JWT
2. **Custom Authentication**: Building custom `AuthenticationProvider` and `UserDetailsService`
3. **JWT Filter**: Creating filters to validate JWT tokens before controllers
4. **Security Context**: How Spring Security maintains the authentication state
5. **Authorization**: Setting up request-based authorization rules
6. **Password Security**: Using Spring's password encoder for secure storage

## 📚 Implementation Steps

The project was built following these steps (see `STEPS.md` for details):

1. Security configuration with stateless session management
2. Password encoder bean
3. Domain-to-security adapter (`UserDetails` implementation)
4. User lookup service (`UserDetailsService`)
5. Authentication provider and manager
6. Auth service and DTOs
7. JWT authentication filter

## ⚠️ Important Notes

- Default test user: `omkar@gmail.com` / `12345` (auto-created on startup)
- **This is a practice project** - not suitable for production use
- JWT secret is stored in plain text (for practice purposes only)
- Password is visible in configuration (for demo only)

## 🔒 Security Considerations

This project focuses on learning Spring Security. For production, consider:
- Secure JWT secret management (use environment variables)
- Token refresh mechanism
- Rate limiting
- HTTPS only
- Proper exception handling
- Input validation and sanitization
- CORS configuration
- Audit logging

## 📖 Resources

- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)
- [JJWT Documentation](https://github.com/jwtk/jjwt)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

## 👨‍💻 Author

This is a practice project for learning Spring Security.

---

**Happy Learning! 🚀**

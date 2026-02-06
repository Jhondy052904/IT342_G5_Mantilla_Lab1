# IT342 Backend - Spring Boot API

A Spring Boot REST API backend with user authentication, registration, and login functionality.

## Features

- ✅ User Registration (`POST /api/auth/register`)
- ✅ User Login (`POST /api/auth/login`)
- ✅ Get Current User (`GET /api/user/me`) - Protected endpoint
- ✅ MySQL Database Integration
- ✅ BCrypt Password Encryption
- ✅ CORS Support

## Requirements

- Java 17 or higher
- Maven 3.8.1 or higher
- MySQL 8.0 or higher

## Setup Instructions

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE it342_db;
```

### 2. Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/it342_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password_here
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

#### Register User
```
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}

Response (201 Created):
{
  "userId": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "token": null,
  "message": "User registered successfully"
}
```

#### Login User
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response (200 OK):
{
  "userId": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "token": "Bearer_1_1675000000000",
  "message": "Login successful"
}
```

### User

#### Get Current User (Protected)
```
GET /api/user/me
Authorization: Bearer <token>

Response (200 OK):
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

## Error Response

```json
{
  "message": "Error description"
}
```

## Project Structure

```
backend/
├── src/
│   └── main/
│       ├── java/com/it342/
│       │   ├── entity/
│       │   │   └── User.java
│       │   ├── repository/
│       │   │   └── UserRepository.java
│       │   ├── service/
│       │   │   └── UserService.java
│       │   ├── controller/
│       │   │   ├── AuthController.java
│       │   │   └── UserController.java
│       │   ├── dto/
│       │   │   ├── RegisterRequest.java
│       │   │   ├── LoginRequest.java
│       │   │   ├── AuthResponse.java
│       │   │   └── UserResponse.java
│       │   ├── config/
│       │   │   └── SecurityConfig.java
│       │   └── IT342Application.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

## Password Security

Passwords are encrypted using BCrypt with a strength factor of 10. The algorithm:
- Takes the user's plain text password
- Generates a random salt
- Hashes the password multiple times
- Stores only the hash in the database

## Future Enhancements

- [ ] JWT Token Implementation
- [ ] Role-based Access Control (RBAC)
- [ ] Email Verification
- [ ] Password Reset Functionality
- [ ] User Profile Update
- [ ] Input Validation (Jakarta Bean Validation)

# Project Setup Guide

## Phase 1: Backend Setup (Current)

### Prerequisites
- **Java 17+** - Download from [oracle.com](https://www.oracle.com/java/technologies/downloads/#java17)
- **Maven 3.8.1+** - Download from [maven.apache.org](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - Download from [mysql.com](https://www.mysql.com/downloads/)

### Backend Installation Steps

1. **Ensure MySQL is running**
   - Windows: Start MySQL from Services
   - Or run: `net start MySQL80` (if set up as a service)

2. **Create the database**
   ```sql
   CREATE DATABASE it342_db;
   ```

3. **Update database credentials in** `backend/src/main/resources/application.properties`
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_actual_password
   ```

4. **Build the backend**
   ```bash
   cd backend
   mvn clean install
   ```

5. **Run the backend**
   ```bash
   mvn spring-boot:run
   ```

   The API will be available at: `http://localhost:8080`

### Testing the API

Use **Postman**, **Insomnia**, **Thunder Client**, or **curl** to test:

#### 1. Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

#### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

#### 3. Get current user (protected)
Use HTTP Basic Auth with email:password
```bash
curl -X GET http://localhost:8080/api/user/me \
  -H "Authorization: Basic dGVzdEBleGFtcGxlLmNvbTpwYXNzd29yZDEyMw=="
```

Or with curl's built-in auth:
```bash
curl -X GET http://localhost:8080/api/user/me \
  --user test@example.com:password123
```

### What's Implemented

✅ User Registration with password encryption  
✅ User Login with credentials validation  
✅ Protected endpoint (GET /api/user/me)  
✅ BCrypt password encryption  
✅ MySQL database integration  
✅ Spring Security configuration  
✅ CORS support for frontend integration  

### Database Schema

The application automatically creates the `users` table with:
- `id` - Primary Key
- `email` - Unique email address
- `password` - BCrypt encrypted password
- `first_name` - User's first name
- `last_name` - User's last name
- `enabled` - Account active status
- `account_locked` - Account lock status

### Notes

- Default JPA DDL setting: `create-drop` (recreates DB on each start)
- For production, change to: `validate` or `update`
- Passwords are hashed with BCrypt strength factor of 10
- Currently uses HTTP Basic Authentication (suitable for testing)

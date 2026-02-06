package com.it342.controller;

import com.it342.dto.RegisterRequest;
import com.it342.dto.LoginRequest;
import com.it342.dto.AuthResponse;
import com.it342.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Register a new user
     * @param request RegisterRequest with email, password, firstName, lastName
     * @return AuthResponse with user details and success/error message
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Validate input
        if (request.getEmail() == null || request.getEmail().isEmpty() ||
            request.getPassword() == null || request.getPassword().isEmpty() ||
            request.getFirstName() == null || request.getFirstName().isEmpty() ||
            request.getLastName() == null || request.getLastName().isEmpty()) {
            return ResponseEntity.badRequest().body(
                new AuthResponse(null, null, null, null, null, "Missing required fields")
            );
        }
        
        AuthResponse response = userService.register(request);
        
        // Check if error occurred
        if (response.getMessage().contains("already") || response.getMessage().contains("exist")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Login user
     * @param request LoginRequest with email and password
     * @return AuthResponse with user details, token and success/error message
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Validate input
        if (request.getEmail() == null || request.getEmail().isEmpty() ||
            request.getPassword() == null || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(
                new AuthResponse(null, null, null, null, null, "Missing email or password")
            );
        }
        
        AuthResponse response = userService.login(request);
        
        // Check if login failed
        if (response.getToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        return ResponseEntity.ok(response);
    }
    
}

package com.it342.service;

import com.it342.dto.RegisterRequest;
import com.it342.dto.LoginRequest;
import com.it342.dto.AuthResponse;
import com.it342.dto.UserResponse;
import com.it342.entity.User;
import com.it342.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, request.getEmail(), null, null, null, "Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setAccountLocked(false);
        
        User savedUser = userRepository.save(user);
        
        return new AuthResponse(
            savedUser.getId(),
            savedUser.getEmail(),
            savedUser.getFirstName(),
            savedUser.getLastName(),
            null,
            "User registered successfully"
        );
    }
    
    public AuthResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        
        if (userOptional.isEmpty()) {
            return new AuthResponse(null, request.getEmail(), null, null, null, "Invalid email or password");
        }
        
        User user = userOptional.get();
        
        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse(null, request.getEmail(), null, null, null, "Invalid email or password");
        }
        
        // Check if account is locked
        if (user.getAccountLocked()) {
            return new AuthResponse(null, request.getEmail(), null, null, null, "Account is locked");
        }
        
        // In a real scenario, generate JWT token here
        String token = "Bearer_" + user.getId() + "_" + System.currentTimeMillis();
        
        return new AuthResponse(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            token,
            "Login successful"
        );
    }
    
    public UserResponse getCurrentUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isEmpty()) {
            return null;
        }
        
        User user = userOptional.get();
        return new UserResponse(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
    }
    
}

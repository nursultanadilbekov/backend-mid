package com.example.midterm_project.service.interfaces;

import com.example.midterm_project.dto.user.UserAuthRequest;
import com.example.midterm_project.dto.user.UserAuthResponse;
import com.example.midterm_project.dto.user.UserRequest;
import com.example.midterm_project.dto.user.UserResponse;
import com.example.midterm_project.entities.User;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService {

    // Retrieve user by ID with token
    UserResponse getById(Long id, String token);

    // Extract user from JWT token
    User getUsernameFromToken(String token);

    // Delete user by ID
    void deleteById(Long id);

    // Update user by ID
    void updateById(Long id, UserRequest userRequest);

    // Get all users
    List<UserResponse> getAll();

    // Register a new user and return authentication response
    UserAuthResponse register(UserAuthRequest userAuthRequest);

    // Authenticate user (login) and return authentication response with tokens
    UserAuthResponse login(UserAuthRequest userAuthRequest);

    void sendNewVerificationEmail(String email) throws MessagingException;

    void verifyEmailToken(String token);
}

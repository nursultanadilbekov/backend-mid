package com.example.midterm_project.service.impl;

import com.example.midterm_project.config.JwtService;
import com.example.midterm_project.dto.user.UserAuthRequest;
import com.example.midterm_project.dto.user.UserAuthResponse;
import com.example.midterm_project.dto.user.UserRequest;
import com.example.midterm_project.dto.user.UserResponse;
import com.example.midterm_project.entities.User;
import com.example.midterm_project.enums.Role;
import com.example.midterm_project.exception.BadRequestException;
import com.example.midterm_project.exception.NotFoundException;
import com.example.midterm_project.mapper.interfaces.UserMapper;
import com.example.midterm_project.repositories.UserRepository;
import com.example.midterm_project.service.interfaces.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserResponse getById(Long id, String token) {
        User actionUser = getUsernameFromToken(token);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new NotFoundException("user not found with id:" + id + "!", HttpStatus.BAD_REQUEST);
        return userMapper.toDto(user.get());
    }

    @Override
    public User getUsernameFromToken(String token) {
        String[] chunks = token.substring(7).split("\\.");
        String username = jwtService.extractUsername(token);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found from token."));
    }

    @Override
    public void deleteById(Long id) {
        if (userRepository.findById(id).isEmpty())
            throw new NotFoundException("user not found with id:" + id + "!", HttpStatus.BAD_REQUEST);
        userRepository.deleteById(id);
    }

    @Override
    public void updateById(Long id, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new NotFoundException("user not found with id:" + id + "!", HttpStatus.BAD_REQUEST);

        // Update the user data as needed, for example, updating the password
        user.get().setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user.get());
    }

    @Override
    public List<UserResponse> getAll() {
        return userMapper.toDtoS(userRepository.findAll());
    }

    @Override
    public UserAuthResponse register(UserAuthRequest userAuthRequest) {
        // Check if the email already exists
        if (userRepository.existsByEmail(userAuthRequest.getEmail())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Email is already in use.");
        }

        // Create a new User entity
        User user = new User();
        user.setEmail(userAuthRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userAuthRequest.getPassword())); // Encrypt the password
        user.setRole(Role.valueOf(userAuthRequest.getRole())); // Set the role from the request

        // Save user to the repository
        userRepository.save(user);

        // Generate JWT tokens
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Return response with the tokens
        UserAuthResponse response = new UserAuthResponse();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setToken(token);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Override
    public UserAuthResponse login(UserAuthRequest userAuthRequest) {
        // Authenticate user with the AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthRequest.getEmail(),
                        userAuthRequest.getPassword()
                )
        );

        // Find the user by email
        User user = userRepository.findByEmail(userAuthRequest.getEmail())
                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "Invalid credentials."));

        // Generate JWT tokens
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Return response with the tokens
        UserAuthResponse response = new UserAuthResponse();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setToken(token);
        response.setRefreshToken(refreshToken);

        return response;
    }
}

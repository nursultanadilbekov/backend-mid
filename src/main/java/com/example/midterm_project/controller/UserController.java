package com.example.midterm_project.controller;

import com.example.midterm_project.dto.user.UserRequest;
import com.example.midterm_project.dto.user.UserResponse;
import com.example.midterm_project.dto.user.UserAuthRequest;
import com.example.midterm_project.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        return userService.getById(id, token);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.updateById(id, userRequest);
    }

    @GetMapping("/getAll")
    public List<UserResponse> users() {
        return userService.getAll();
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserAuthRequest userAuthRequest) throws MessagingException {
        userService.register(userAuthRequest);
        return "Registration successful. Please verify your email address.";
    }

    @PostMapping("/request-new-verification")
    public String requestNewVerificationEmail(@RequestBody UserAuthRequest userAuthRequest) throws MessagingException, jakarta.mail.MessagingException {
        userService.sendNewVerificationEmail(userAuthRequest.getEmail());
        return "A new verification email has been sent.";
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam String token) {
        userService.verifyEmailToken(token);
        return "Email successfully verified!";
    }
}

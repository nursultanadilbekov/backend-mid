package com.example.midterm_project.controller;

import com.example.midterm_project.dto.user.UserRequest;
import com.example.midterm_project.dto.user.UserResponse;
import com.example.midterm_project.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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


}
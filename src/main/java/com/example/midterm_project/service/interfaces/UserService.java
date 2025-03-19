package com.example.midterm_project.service.interfaces;

import com.example.midterm_project.dto.user.UserRequest;
import com.example.midterm_project.dto.user.UserResponse;
import com.example.midterm_project.entities.User;

import java.util.List;

public interface UserService {

    UserResponse getById(Long id, String t);

    User getUsernameFromToken(String token);

    void deleteById(Long id);

    void updateById(Long id, UserRequest userRequest);

    List<UserResponse> getAll();
}
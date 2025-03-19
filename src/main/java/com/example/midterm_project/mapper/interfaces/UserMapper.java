package com.example.midterm_project.mapper.interfaces;

import com.example.midterm_project.dto.user.UserResponse;
import com.example.midterm_project.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    UserResponse toDto(User user);

    List<UserResponse> toDtoS(List<User> all);
}
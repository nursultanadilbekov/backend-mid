package com.example.midterm_project.mapper.impl;

import com.example.midterm_project.dto.user.UserResponse;
import com.example.midterm_project.entities.User;
import com.example.midterm_project.mapper.interfaces.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        return userResponse;
    }

    @Override
    public List<UserResponse> toDtoS(List<User> all) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user: all){
            userResponses.add(toDto(user));
        }
        return userResponses;
    }
}
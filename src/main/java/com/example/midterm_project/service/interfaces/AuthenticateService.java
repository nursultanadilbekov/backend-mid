package com.example.midterm_project.service.interfaces;

import com.example.midterm_project.dto.user.UserAuthRequest;
import com.example.midterm_project.dto.user.UserAuthResponse;
import com.example.midterm_project.entities.User;

import javax.management.relation.Relation;

public interface AuthenticateService {
    void register(UserAuthRequest userAuthRequest);

    UserAuthResponse login(UserAuthRequest userAuthRequest);

    User getUsernameFromToken(String token);
}

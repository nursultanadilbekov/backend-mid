package com.example.midterm_project.service.impl;

import com.example.midterm_project.config.JwtService;
import com.example.midterm_project.entities.User;
import com.example.midterm_project.repositories.UserRepository;
import com.example.midterm_project.service.interfaces.UserService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtService jwtService;

    }
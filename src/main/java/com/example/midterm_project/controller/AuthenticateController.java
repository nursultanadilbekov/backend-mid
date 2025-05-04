package com.example.midterm_project.controller;

import com.example.midterm_project.dto.user.RefreshTokenRequest;
import com.example.midterm_project.dto.user.UserAuthRequest;
import com.example.midterm_project.dto.user.UserAuthResponse;
import com.example.midterm_project.service.interfaces.AuthenticateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticateController {

    private final AuthenticateService authenticateService;

    @PostMapping("/register")
    public void register(@RequestBody UserAuthRequest userAuthRequest) {
        authenticateService.register(userAuthRequest);
    }

    @PostMapping("/login")
    public UserAuthResponse login(@RequestBody UserAuthRequest userAuthRequest) {
        return authenticateService.login(userAuthRequest);
    }

    @PostMapping("/refresh")
    public UserAuthResponse refresh(@RequestBody RefreshTokenRequest request) {
        return authenticateService.refreshToken(request.getRefreshToken());
    }
}

package com.example.midterm_project.controller;


import com.example.midterm_project.dto.user.UserAuthRequest;
import com.example.midterm_project.dto.user.UserAuthResponse;
import com.example.midterm_project.service.interfaces.AuthenticateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth") // it's for all endpoints in the class: localhost:8080/product/...
@AllArgsConstructor
public class AuthenticateController {
    private final AuthenticateService authenticateService;
    @PostMapping("/register")
    public void register(@RequestBody UserAuthRequest userAuthRequest){
      }

    @PostMapping("/login")
    public UserAuthResponse userAuthRequest(@RequestBody UserAuthRequest userAuthRequest){
    }
}

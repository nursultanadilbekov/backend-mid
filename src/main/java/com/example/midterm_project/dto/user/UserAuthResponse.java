package com.example.midterm_project.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthResponse {
    private String email;
    private String role;
    private String token;
    private String refreshToken;
}

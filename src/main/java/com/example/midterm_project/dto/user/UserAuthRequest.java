package com.example.midterm_project.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthRequest {
    private String email;
    private String password;
    private String name;
    private Integer age;
    private String role;
}

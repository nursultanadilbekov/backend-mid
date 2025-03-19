package com.example.midterm_project.dto.repairer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RepairerRequest {
    private String name;
    private int age;
    private int cost_of_work;
    private String experience;
}


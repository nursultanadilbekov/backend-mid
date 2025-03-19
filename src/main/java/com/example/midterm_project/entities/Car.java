package com.example.midterm_project.entities;

import com.example.midterm_project.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="cars_table")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int age;
    private String number;
    @Enumerated(EnumType.STRING)
    private String model;

    @ManyToOne
    Repairer repairer;

    @ManyToOne
    Customer customer;
}

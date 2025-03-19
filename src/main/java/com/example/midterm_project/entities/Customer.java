package com.example.midterm_project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name="customers_table")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany()
    List<com.example.repair_station.entities.Car> cars;

    @OneToOne(mappedBy = "customer")
    User user;

}

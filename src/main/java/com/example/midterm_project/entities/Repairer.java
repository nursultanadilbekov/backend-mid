package com.example.midterm_project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="repairers_table")
public class Repairer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int age;
    private int cost_of_work;
    private String experience;

    @OneToMany(cascade = CascadeType.ALL)
    List<com.example.repair_station.entities.Car> repairercars;

    @OneToOne(mappedBy = "repairer")
    User user;
}

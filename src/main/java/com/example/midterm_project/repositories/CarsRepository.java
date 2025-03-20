package com.example.midterm_project.repositories;

import com.example.midterm_project.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarsRepository extends JpaRepository<Car,Long> {

    Optional<Car> findByNumber(String tra);
}

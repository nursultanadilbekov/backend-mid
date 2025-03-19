package com.example.midterm_project.mapper.interfaces;

import com.example.midterm_project.dto.cars.CarsResponse;
import com.example.midterm_project.entities.Car;

import java.util.List;

public interface CarsMapper {
    CarsResponse toDto(Car car);

    List<CarsResponse> toDtos(List<Car>all);
}

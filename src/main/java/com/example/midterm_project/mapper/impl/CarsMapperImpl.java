package com.example.midterm_project.mapper.impl;

import com.example.midterm_project.dto.cars.CarsResponse;
import com.example.midterm_project.entities.Car;
import com.example.midterm_project.mapper.interfaces.CarsMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarsMapperImpl implements CarsMapper {

    @Override
    public CarsResponse toDto(Car car){
        CarsResponse carsResponse=new CarsResponse();
        carsResponse.setId(car.getId());
        carsResponse.setModel(String.valueOf(car.getModel()));
        carsResponse.setAge(car.getAge());
        carsResponse.setNumber(car.getNumber());

        return carsResponse;
    }

    @Override
    public List<CarsResponse>toDtos(List<Car>all){
        List<CarsResponse>carsResponses=new ArrayList<>();
        for(Car car:all){
            carsResponses.add(toDto(car));
        }
        return carsResponses;
    }
}

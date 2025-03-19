package com.example.midterm_project.service.impl;

import com.example.midterm_project.dto.cars.CarsRequest;
import com.example.midterm_project.dto.cars.CarsResponse;
import com.example.midterm_project.entities.Car;
import com.example.midterm_project.entities.Repairer;
import com.example.midterm_project.repositories.CarsRepository;
import com.example.midterm_project.repositories.RepairerRepository;
import com.example.midterm_project.service.interfaces.CarsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarsServiceImpl implements CarsService {

    private final CarsRepository carsRepository;
    private RepairerRepository repairerRepository;


    @Override
    public void add(CarsRequest carsRequest, String token) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, CarsRequest carsRequest) {

    }

    @Override
    public CarsResponse find(Long id) {
        return null;
    }

    @Override
    public List<CarsResponse> findAll() {
        return List.of();
    }

    @Override
    public void register(CarsRequest carsRequest, Long repairerId) {

    }

    @Override
    public void drop(Long repairerId, Long id) {

    }
}
